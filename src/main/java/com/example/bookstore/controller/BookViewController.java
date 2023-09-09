package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookViewController {

    private final BookService bookService;

    @GetMapping("/")
    public String viewHomePage(Model model, @RequestParam(defaultValue = "1") int pageNo) {
        Page<Book> page = bookService.findPaginated(pageNo, 50);
        model.addAttribute("books", page);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        return "index";
    }
}
