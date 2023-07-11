package com.example.books.services;

import com.example.books.entities.ArchivePerson;
import com.example.books.entities.Person;
import com.example.books.repositories.ArchivePersonRepository;
import com.example.books.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    @Autowired
    private final ArchivePersonRepository archivePersonRepository;

    public PersonService(PersonRepository personRepository, ArchivePersonRepository archivePersonRepository) {
        this.personRepository = personRepository;
        this.archivePersonRepository = archivePersonRepository;
    }
    @Transactional(readOnly = true)
    public List<Person> findAll(){
        return personRepository.findAll();
    }
    @Transactional
    public  void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person person){
        Optional<Person> oldPerson = personRepository.findById(id);

        oldPerson.get().setAge(person.getAge());
        oldPerson.get().setName(person.getName());
        oldPerson.get().setEmail(person.getEmail());
        oldPerson.get().setPassword(person.getPassword());

        personRepository.save(oldPerson.get());
    }

    @Transactional
    public Optional<Person> findById(int id){
        return personRepository.findById(id);
    }

    @Transactional
    public void deleteUserByIdToArchive(int id){
        Optional<Person> person = personRepository.findById(id);
        person.get().setDeleted(true);
        personRepository.save(person.get());

        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        ArchivePerson archivePerson = new ArchivePerson(person.get().getPersonId(), new Date(), authentication.getName());
        archivePersonRepository.save(archivePerson);
    }

    @Transactional
    public List<Person> ListDebtors(){
        return personRepository.ListDebtors();
    }

    @Transactional
    public Optional<Person> findByemail(String email){
        return personRepository.findByemail(email);
    }

}