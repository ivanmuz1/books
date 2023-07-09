package com.example.books.services;

import com.example.books.entities.Form;
import com.example.books.entities.Person;
import com.example.books.repositories.FormRepository;
import com.example.books.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
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

    public void addNewEntry(Form form) { //изменить
        Optional<Person> currentPerson = personRepository.findById(1);
        form.setReaderId(currentPerson.get().getPersonId());
        form.setDateDelivery(new Date());
        formRepository.save(form);
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