package com.example.books.controllers;

import com.example.books.dto.EmailRequest;
import com.example.books.email.SendService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private SendService sendService;
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {

        sendService.send();

        return "Email sent successfully";
    }
}
