package com.example.books.dto;

import com.example.books.entities.Book;
import com.example.books.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    String Name;

    Integer Age;

    String Email;

    Role role;

    List<Book> bookList;
}
