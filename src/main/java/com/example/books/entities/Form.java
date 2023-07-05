package com.example.books.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "form")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Form {

    @Id
    @Column(name = "form_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer FormId;

    @Column(name = "date_receipt")
    Date DateReceipt;

    @Column(name = "date_returned")
    Date DateReturned;

    @Column(name = "reader_id")
    Integer ReaderId;

    @Column(name = "book_id")
    Integer BookId;

    @Column(name = "penalties")
    Integer Penalties;

    @Column(name = "days_in_arrears")
    Integer DaysInArrears;

}
