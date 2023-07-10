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
    private Integer FormId;

    @Column(name = "date_receipt")
    private Date DateReceipt;

    @Column(name = "date_delivery")
    private Date DateDelivery;

    @Column(name = "return_date")
    private Date DateReturn;

    @Column(name = "reader_id")
    private Integer ReaderId;

    @Column(name = "book_id")
    private Integer BookId;

    @Column(name = "penalties")
    private Integer Penalties;

    @Column(name = "days_in_arrears")
    private Integer DaysInArrears;

}
