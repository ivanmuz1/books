package com.example.books.services;

import com.example.books.entities.Book;
import com.example.books.entities.Person;
import com.example.books.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @Transactional(readOnly = true)
    public List<Person> findAll(){
        return personRepository.findAll();
    }
    @Transactional
    public void save(Person person){
        personRepository.save(person);
    } //создание новоего пользователя

    @Transactional
    public void updatePerson(int id, Person person){
        Optional<Person> oldPerson = personRepository.findById(id);

        oldPerson.get().setAge(person.getAge());
        oldPerson.get().setName(person.getName());
        oldPerson.get().setEmail(person.getEmail());
        oldPerson.get().setPassword(person.getPassword());

        personRepository.save(oldPerson.get());
    } //

    @Transactional
    public Optional<Person> findById(int id){
        return personRepository.findById(id);
    }

    @Transactional
    public void deleteUserByIdToArchive(int id){
        Optional<Person> person = personRepository.findById(id);
        person.get().setDeleted(true);
        personRepository.save(person.get());
    }
}