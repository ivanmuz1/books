package com.example.books.repositories;

import com.example.books.entities.ArchiveBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveBookRepository extends JpaRepository<ArchiveBooks, Integer> {
}
