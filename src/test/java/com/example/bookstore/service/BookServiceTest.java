package com.example.bookstore.service;

import com.example.bookstore.dto.BookCreateRequest;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookUpdateRequest;
import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testCreateBookSuccess() {
        BookCreateRequest request = createCreateRequest();
        Book mappedBook = createMappedBook();
        Book savedBook = createBook();
        BookDTO returnedDto = createBookDTO();

        when(bookRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(modelMapper.map(request, Book.class)).thenReturn(mappedBook);
        when(bookRepository.save(mappedBook)).thenReturn(savedBook);
        when(modelMapper.map(savedBook, BookDTO.class)).thenReturn(returnedDto);

        BookDTO result = bookService.createBook(request);

        assertEquals(returnedDto, result);
    }

    @Test
    public void testCreateBookFailDueToNameExists() {
        BookCreateRequest request = createCreateRequest();
        Book existingBook = createBook();

        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(existingBook));

        assertThrows(ResponseStatusException.class, () -> bookService.createBook(request));
    }

    @Test
    public void testListBooks() {
        Book book = createBook();
        BookDTO bookDTO = createBookDTO();

        when(bookRepository.findAll(any(Sort.class))).thenReturn(List.of(book));
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        List<BookDTO> result = bookService.listBooks();

        assertEquals(1, result.size());
        assertEquals(bookDTO, result.get(0));
    }


    @Test
    public void testUpdateBook() {
        Long bookId = 12L;
        BookUpdateRequest updateRequest = createUpdateRequest();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(createBook()));

        bookService.updateBook(bookId, updateRequest);

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void testUpdateBookFailDueToNotFound() {
        Long bookId = 1L;
        BookUpdateRequest request = createUpdateRequest();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> bookService.updateBook(bookId, request));
    }

    private BookCreateRequest createCreateRequest() {
        return new BookCreateRequest("Book Name", "Author Name", BigDecimal.valueOf(91.99));
    }

    private BookUpdateRequest createUpdateRequest() {
        return new BookUpdateRequest("New Book Name", "New Author", BigDecimal.valueOf(99.99));
    }

    private Book createMappedBook() {
        return new Book(null, "Book Name", "Author Name", null, BigDecimal.valueOf(91.99));
    }

    private Book createBook() {
        return new Book(12L, "Book Name", "Author Name", LocalDateTime.now(), BigDecimal.valueOf(91.99));
    }

    private BookDTO createBookDTO() {
        return new BookDTO(12L, "Book Name", "Author Name", LocalDateTime.now(), BigDecimal.valueOf(91.99));
    }
}