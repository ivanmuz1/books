package com.example.books.services;

import com.example.books.entities.Book;
import com.example.books.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Transactional()
    public List<Book> findAll(){
        return bookRepository.findAll();
    }
    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    } //создание новой книги

    @Transactional
    public void updateBook(int id, Book newBook){
        Optional<Book> oldBook = bookRepository.findById(id);

        oldBook.get().setTitle(newBook.getTitle());
        oldBook.get().setAuthor(newBook.getAuthor());

        bookRepository.save(oldBook.get());
    } //редактирование существующей книги

    @Transactional
    public Optional<Book> findById(int id){ //просмотр свойств существующей книги!
        return bookRepository.findById(id);
    }

    @Transactional
    public void deleteUserByIdToArchive(int id){
        Optional<Book> book = bookRepository.findById(id);
        book.get().setDeleted(true);
        bookRepository.save(book.get());
    }
}
