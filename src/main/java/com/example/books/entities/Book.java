package com.example.books.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer BookId;

    @Column(name = "title")
    String Title;

    @Column(name = "author")
    String Author;

    @Column(name = "deleted")
    Boolean deleted;

    @ManyToMany(mappedBy = "person")
    List<Person> personList;

}