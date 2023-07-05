package com.example.books.controllers;

import com.example.books.entities.Form;
import com.example.books.report.ReportToExcel;
import com.example.books.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@RestController
public class FormController {

    @Autowired
    FormService formService;

    @Autowired
    ReportToExcel reportToExcel;

    @PostMapping("/cheek/{Reader_Id}")
    public void cheek(@PathVariable int Reader_Id){
        formService.DaysInArrears(Reader_Id);
    }

    @PostMapping("/gotoexcle")
    public void letsgo(@RequestBody String date) throws IOException, ParseException {
        reportToExcel.generateReport(date);
    }

}
