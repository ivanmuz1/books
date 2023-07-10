package com.example.books.entities;

import com.example.books.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor
public class Person {
    public Person(String name, Integer age, String email, String password, Role role) {
        Name = name;
        Age = age;
        this.email = email;
        Password = password;
        this.role = role;
    }

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer PersonId;

    @Column(name = "name")
    private String Name;

    @Column(name = "age")
    private Integer Age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String Password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "form",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    List<Book> books;

}
