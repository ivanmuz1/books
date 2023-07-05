package com.example.books.entities;

import com.example.books.entities.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer PersonId;

    @Column(name = "name")
    String Name;

    @Column(name = "age")
    Integer Age;

    @Column(name = "email")
    String Email;

    @Column(name = "password")
    String Password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "deleted")
    Boolean deleted;

    @ManyToMany
    @JoinTable(
            name = "form",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    List<Book> bookList;

}
