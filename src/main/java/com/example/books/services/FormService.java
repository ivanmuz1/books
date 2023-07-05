package com.example.books.services;

import com.example.books.entities.Form;
import com.example.books.repositories.FormRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {

    @Autowired
    private final FormRepository formRepository;


    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public List<Form> findAll(){
        return formRepository.findAll();
    }

}