package com.example.books.security.jwt;

import com.example.books.entities.Person;
import com.example.books.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var userByName = this.personRepository.findByemail(login);
        Person person = userByName.isEmpty() ? personRepository.findByemail(login).get():userByName.get();
        return PersonPrinciple.build(person);
    }
}