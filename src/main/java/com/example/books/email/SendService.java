package com.example.books.email;


import com.example.books.entities.Form;
import com.example.books.services.BookService;
import com.example.books.services.FormService;
import com.example.books.services.PersonService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


@Service
@EnableScheduling
public class SendService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FormService formService;

    @Autowired
    private PersonService personService;

    @Autowired
    private BookService bookService;

   // @Scheduled(fixedRate = 30000)
    public void send() throws MessagingException {
        List<Form> debtors = formService.EmailWithDayDelay();
        Date DateCur = new Date();
        debtors.stream().forEach(
                i->{
                    SimpleMailMessage mail = new SimpleMailMessage();
                    mail.setTo(personService.findById(i.getReaderId()).get().getEmail());
                    mail.setSubject("Долг за книгу!");
                    Integer CountDelay = (int) ChronoUnit.DAYS.between(i.getDateDelivery().toInstant(), DateCur.toInstant());
                    String BookName = bookService.findById(i.getBookId()).get().getTitle();
                    mail.setText("Книга: " + BookName + " " + "Количество просроченных дней:" + CountDelay + " " + "Пенни: " + " " + CountDelay * 5);
                    javaMailSender.send(mail);
                }
        );
    }

}
