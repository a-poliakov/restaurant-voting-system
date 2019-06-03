package ru.apolyakov.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
public class SecurityRestController {

    @RequestMapping(value = "/password/{password}", method = RequestMethod.GET)
    public String password(@PathVariable("password")String password){
        String value = new BCryptPasswordEncoder().encode(password);
        return value;
    }
}
