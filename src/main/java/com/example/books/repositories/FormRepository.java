package com.example.books.repositories;

import com.example.books.entities.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
    @Query(value = "select * from form where reader_id = ?", nativeQuery = true)
    List<Form> findAllByIdUser(int id);

    @Query(value = "select * from form where extract(month from date_delivery) = ?\n" +
            "    and  extract(year from date_delivery) = ?", nativeQuery = true)
    List<Form> findUserByMonth(int month, int year);
}
