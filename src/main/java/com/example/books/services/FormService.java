package com.example.books.services;

import com.example.books.entities.Form;
import com.example.books.repositories.FormRepository;
import com.example.books.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}