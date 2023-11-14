package com.example.shop.controller;

import com.example.shop.model.Users;
import com.example.shop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users users) {
        Users registeredUser = usersService.registerUsers(users);

        if (registeredUser != null) {

            return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error al registrar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
