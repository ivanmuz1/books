package com.example.books.controllers;

import com.example.books.entities.Person;
import com.example.books.security.jwt.JwtService;
import com.example.books.security.jwt.PersonPrinciple;
import com.example.books.security.message.requests.LoginRequest;
import com.example.books.security.message.requests.RegisterRequest;
import com.example.books.security.message.responses.JwtResponse;
import com.example.books.security.message.responses.ResponseMessage;
import com.example.books.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import static com.example.books.entities.enums.Role.ROLE_USER;

@Controller
public class LoginController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final PersonService personService;

    @Autowired
    private final PasswordEncoder encoder;

    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager, PersonService personService, PasswordEncoder encoder) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.personService = personService;
        this.encoder = encoder;
    }

    @PostMapping("/reg")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {

        Person person = new Person(
                request.getName(),
                request.getAge(),
                request.getEmail(),
                encoder.encode( request.getPassword()),
                ROLE_USER,
                false
        );
        personService.save(person);


        PersonPrinciple userPrinciple = PersonPrinciple.build(person);
        String jwtToken = jwtService.generateToken(userPrinciple);

        System.out.println(jwtToken);

        return new ResponseEntity<>(new ResponseMessage("User registration is successful"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        Person userEntity = this.personService.findByemail(request.getEmail()).get();

        System.out.println(userEntity.getEmail()+" "+userEntity.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userEntity.getEmail(), request.getPassword()));


        String jwt = jwtService.generateToken((PersonPrinciple) authentication.getPrincipal());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }
}
