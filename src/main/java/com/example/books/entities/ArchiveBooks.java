package com.example.books.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "archive_books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveBooks {

    @Id
    @Column(name = "book_id")
    private Integer BookId;

    @Column(name = "date_deletion")
    private Date DateDeletion;

    @Column(name = "who_deleted")
    private String WhoDeleted;

}
