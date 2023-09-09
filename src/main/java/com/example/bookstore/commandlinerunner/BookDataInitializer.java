package com.example.bookstore.commandlinerunner;

import com.example.bookstore.dto.BookCreateRequest;
import com.example.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookDataInitializer implements CommandLineRunner {

    private final BookService bookService;
    @Value("${app.initializeBooks:false}")
    private boolean initializeBooks;

    @Override
    public void run(String... args) {
        if (initializeBooks) {
            for (int i = 1; i <= 100; i++) {
                BookCreateRequest request = new BookCreateRequest("Book " + i, "Author " + i);
                bookService.createBook(request);
            }
        }
    }
}
