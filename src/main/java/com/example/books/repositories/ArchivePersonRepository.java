package com.example.books.repositories;

import com.example.books.entities.ArchivePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivePersonRepository extends JpaRepository<ArchivePerson, Integer> {
}
