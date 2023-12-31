package com.example.books.controllers;

import com.example.books.dto.PersonDTO;
import com.example.books.entities.Form;
import com.example.books.entities.Person;
import com.example.books.services.FormService;
import com.example.books.services.PersonService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    FormService formService;

    @PostMapping("/user")
    public List<Person> user(@RequestBody Person person){
        personService.save(person);
        return personService.findAll();
    }

    @PostMapping("/user/save")
    public List<Person> saveUser(@RequestBody Person person){
        personService.save(person);
        return personService.findAll();
    }

    @PostMapping("/update/{PersonId}")
    public Optional<Person> update(@PathVariable int PersonId, @RequestBody Person person){
        personService.updatePerson(PersonId, person);
        return personService.findById(PersonId);
    }

    @PostMapping("/user/{PersonId}")
    public Optional<Person> findUserById(@PathVariable int PersonId){
        return personService.findById(PersonId);
    }

    @DeleteMapping("/user/delete/{PersonId}")
    public void delete(@PathVariable int PersonId){
        personService.deleteUserByIdToArchive(PersonId);
    }

    @PostMapping("/add")
    public Optional<Form> addBookToUser(@RequestBody Form form){
        return formService.addNewEntry(form);
    }

    @GetMapping("/user/{PersonID}/showBooks")
    public PersonDTO show(@PathVariable int PersonID){
        Optional<Person> person = personService.findById(PersonID);

        PersonDTO personDTO = new PersonDTO(
                person.get().getName(),
                person.get().getAge(),
                person.get().getEmail(),
                person.get().getRole(),
                person.get().getBooks()
        );

       return personDTO;
    }

}