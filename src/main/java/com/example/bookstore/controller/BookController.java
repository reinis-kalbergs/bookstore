package com.example.bookstore.controller;

import com.example.bookstore.dto.BookCreateRequest;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookUpdateRequest;
import com.example.bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookDTO createBook(@RequestBody @Valid BookCreateRequest request) {
        return bookService.createBook(request);
    }

    @GetMapping
    public List<BookDTO> listBooks() {
        return bookService.listBooks();
    }

    @PutMapping("/{bookId}")
    public BookDTO updateBook(@PathVariable Long bookId, @RequestBody @Valid BookUpdateRequest request) {
        return bookService.updateBook(bookId, request);
    }
}
