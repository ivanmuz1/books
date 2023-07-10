package com.example.books.email;


import com.example.books.entities.Book;
import com.example.books.entities.Form;
import com.example.books.entities.Person;
import com.example.books.repositories.PersonRepository;
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
import java.util.Optional;


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


    //@Scheduled(fixedRate = 5000)
    public void send() throws MessagingException {

        List<Person> personList = personService.findAll();

        personList.stream().forEach( //бегу по формуляру, который состоит из должников
                person->{
                    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

                    String subject = "Просрочка книги";
                    simpleMailMessage.setSubject(subject);

                    formService.DaysInArrears(person.getPersonId());

                    List<Form> formList = formService.EmailForPerson(person.getPersonId());

                    StringBuilder textBuilder = new StringBuilder();
                    textBuilder.append("Здравствуйте, ").append(person.getName()).append("!\n").append("Вы должны слеующие книги:\n");

                    formList.stream().forEach(
                            form -> {
                                textBuilder.append("Книга: ").append(bookService.findById(form.getBookId()).get().getTitle()).append(", дней просрочки:")
                                        .append(form.getDaysInArrears()).append(", пенни:").append(form.getPenalties()).append("\n");
                            }
                    );

                    simpleMailMessage.setTo(person.getEmail());
                    simpleMailMessage.setText(textBuilder.toString());
                    javaMailSender.send(simpleMailMessage);

                }
        );
    }

}
