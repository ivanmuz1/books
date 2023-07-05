package com.example.books.controllers;

import com.example.books.dto.PersonDTO;
import com.example.books.entities.Person;
import com.example.books.services.PersonService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

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

    @PostMapping("/user/update/{PersonId}")
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
        personService.deleteById(PersonId);
    }

    @GetMapping("/user/{PersonID}/books")
    public PersonDTO show(@PathVariable int PersonID){
        Optional<Person> person = personService.findById(PersonID);

        PersonDTO personDTO = new PersonDTO(
                person.get().getName(),
                person.get().getAge(),
                person.get().getEmail(),
                person.get().getRole(),
                person.get().getBookList()
        );

       return personDTO;
    }

}