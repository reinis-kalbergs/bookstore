package com.example.bookstore.commandlinerunner;

import com.example.bookstore.dto.BookCreateRequest;
import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookDataInitializer implements CommandLineRunner {

    private final BookService bookService;
    private final BookRepository bookRepository;
    @Value("${app.initializeBooks:false}")
    private boolean initializeBooks;

    @Override
    public void run(String... args) {
        if (initializeBooks) {
            Optional<Book> firstBook = bookRepository.findByName("Book 1");

            if (firstBook.isPresent()) {
                log.info("Fake data has already been generated");
            } else {
                log.info("Generating 100 fake books");
                for (int i = 1; i <= 100; i++) {
                    BookCreateRequest request = new BookCreateRequest("Book " + i, "Author " + i, BigDecimal.valueOf(i + 0.99));
                    bookService.createBook(request);
                }
                log.info("Fake data generation is complete");
            }
        }
    }
}
