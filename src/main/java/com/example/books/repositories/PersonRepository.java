package com.example.books.repositories;

import com.example.books.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query(value = "select * from person inner join form f on person.person_id = f.reader_id\n" +
            "where now() > date_delivery", nativeQuery = true)
    List<Person> ListDebtors();

}
