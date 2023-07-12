package com.example.books.controllers;

import com.example.books.entities.Book;
import com.example.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/library")
@RestController
public class BooksController {

    @Autowired
    private final BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/allbooks")
    public Iterable<Book> books(){
        return bookService.findAll();
    }

    @PostMapping("/book/save")
    public List<Book> saveBook(@RequestBody Book book){
        book.setDeleted(false);
        bookService.save(book);
        return bookService.findAll();
    }

    @PostMapping("/book/update/{BookId}")
    public Book update(@PathVariable int BookId, @RequestBody Book book){
        bookService.updateBook(BookId, book);
        return bookService.findById(BookId);
    }

    @PostMapping("/{BookId}")
    public Book findById(@PathVariable int BookId){
        return bookService.findById(BookId);
    }

    @DeleteMapping("/book/delete/{BookId}")
    public void delete(@PathVariable int BookId){
        bookService.deleteBookByIdToArchive(BookId);
    }

}
