package com.example.books.services;

import com.example.books.entities.Form;
import com.example.books.entities.Person;
import com.example.books.repositories.FormRepository;
import com.example.books.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired
    private final FormRepository formRepository;


    @Autowired
    private final PersonRepository personRepository;


    public FormService(FormRepository formRepository, PersonRepository personRepository) {
        this.formRepository = formRepository;
        this.personRepository = personRepository;
    }

    public List<Form> findAll(){
        return formRepository.findAll();
    }

    public List<Form> EmailForPerson(int id) {
        return formRepository.EmailForPerson(id);
    }

    public Optional<Form> addNewEntry(Integer book_id, Date DateDelivery) {
        Form form = new Form();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> person = personRepository.findByemail(auth.getName());

        form.setReaderId(person.get().getPersonId());
        form.setDateReceipt(new Date());
        form.setDateDelivery(DateDelivery);
        form.setBookId(book_id);

        formRepository.save(form);
        return formRepository.findById(form.getFormId());
    }

    public List<Form> DaysInArrears(int ReaderId){
        Date DateCur = new Date();
        List<Form> formOfUser = formRepository.findAllByIdUser(ReaderId);
        formOfUser.stream().forEach(i->{
                  long diffInDaysForDelay = ChronoUnit.DAYS.between(i.getDateDelivery().toInstant(), DateCur.toInstant());

                    if(i.getDateReturn() != null && i.getDateDelivery().after(i.getDateReturn())){
                        i.setPenalties(0);
                        i.setDaysInArrears(0);
                    } else if (i.getDateReturn() == null && i.getDateDelivery().after(DateCur)) {
                        i.setPenalties(0);
                        i.setDaysInArrears(0);
                    } else if (i.getDateReturn() == null && !(i.getDateDelivery().after(DateCur))) {
                        i.setDaysInArrears((int) diffInDaysForDelay);
                        i.setPenalties((int) ((diffInDaysForDelay) * 5));
                    } else if (i.getDateReturn() != null && i.getDateReturn().after(i.getDateDelivery()) ) {
                        i.setPenalties((int) ChronoUnit.DAYS.between(i.getDateDelivery().toInstant(), i.getDateReturn().toInstant())  * 5);
                        i.setDaysInArrears((int)ChronoUnit.DAYS.between(i.getDateDelivery().toInstant(), i.getDateReturn().toInstant()));
                    }
                    formRepository.save(i);
                }
        );
        return formOfUser;
    }




}