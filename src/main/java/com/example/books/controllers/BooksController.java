package com.example.books.controllers;

import com.example.books.entities.Book;
import com.example.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class BooksController {

    @Autowired
    BookService bookService;

    @GetMapping("/allbooks")
    public Iterable<Book> books(){
        return bookService.findAll();
    }

    @PostMapping("/book/save")
    public List<Book> saveBook(@RequestBody Book book){
        bookService.save(book);
        return bookService.findAll();
    }

    @PostMapping("/book/update/{BookId}")
    public Optional<Book> update(@PathVariable int BookId, @RequestBody Book book){
        bookService.updateBook(BookId, book);
        return bookService.findById(BookId);
    }

    @PostMapping("/{BookId}")
    public Optional<Book> findById(@PathVariable int BookId){
        return bookService.findById(BookId);
    }

    @DeleteMapping("/book/delete/{BookId}")
    public void delete(@PathVariable int BookId){
        bookService.deleteUserByIdToArchive(BookId);
    }

}
