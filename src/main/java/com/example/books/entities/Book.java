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
    private Integer BookId;

    @Column(name = "title")
    private String Title;

    @Column(name = "author")
    private String Author;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToMany(mappedBy = "books")
    List<Person> persons;

}