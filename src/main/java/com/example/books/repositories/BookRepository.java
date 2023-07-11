package com.example.books.repositories;

import com.example.books.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select * from book where deleted = false ", nativeQuery = true)
    List<Book> findAllByDeletedFalse();

    @Query(value = "select * from book where deleted = false and book_id = ?", nativeQuery = true)
    Book findByIdDeletedFalse(int id);
}
