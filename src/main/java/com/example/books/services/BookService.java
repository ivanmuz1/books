package com.example.books.services;

import com.example.books.entities.ArchiveBooks;
import com.example.books.entities.Book;
import com.example.books.repositories.ArchiveBookRepository;
import com.example.books.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final ArchiveBookRepository archiveBookRepository;

    public BookService(BookRepository bookRepository, ArchiveBookRepository archiveBookRepository) {
        this.bookRepository = bookRepository;
        this.archiveBookRepository = archiveBookRepository;
    }
    @Transactional()
    public List<Book> findAll(){
        return bookRepository.findAllByDeletedFalse();
    }
    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(int id, Book newBook){
        Optional<Book> oldBook = bookRepository.findById(id);

        oldBook.get().setTitle(newBook.getTitle());
        oldBook.get().setAuthor(newBook.getAuthor());

        bookRepository.save(oldBook.get());
    }

    @Transactional
    public Book findById(int id){ //просмотр свойств существующей книги!
        return bookRepository.findByIdDeletedFalse(id);
    }

    @Transactional
    public void deleteBookByIdToArchive(int id){
        Book book = bookRepository.findByIdDeletedFalse(id);
        book.setDeleted(true);
        bookRepository.save(book);

        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        ArchiveBooks archiveBooks = new ArchiveBooks(book.getBookId(), new Date(), authentication.getName());
        archiveBookRepository.save(archiveBooks);
    }
}
