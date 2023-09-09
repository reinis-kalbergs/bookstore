package com.example.bookstore.service;

import com.example.bookstore.dto.BookCreateRequest;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookDTO createBook(BookCreateRequest request) {
        Optional<Book> existingBook = bookRepository.findByName(request.getName());
        if (existingBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book with this name already exists!");
        }
        Book book = modelMapper.map(request, Book.class);
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    public List<BookDTO> listBooks() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "dateAdded"))
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    public Page<Book> findPaginated(int pageNo, int pageSize) {
        return bookRepository.findAll(PageRequest.of(pageNo - 1, pageSize));
    }

}
