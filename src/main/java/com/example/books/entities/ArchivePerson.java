package com.example.books.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "archive_person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArchivePerson {

    @Id
    @Column(name = "person_id")
    private Integer PersonId;

    @Column(name = "date_deletion")
    private Date DateDeletion;

    @Column(name = "who_deleted")
    private String WhoDeleted;

}
