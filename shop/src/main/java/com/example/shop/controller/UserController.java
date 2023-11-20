package com.example.shop.controller;

import com.example.shop.dto.LoginDto;
import com.example.shop.dto.RegisterDto;
import com.example.shop.model.Role;
import com.example.shop.model.User;
import com.example.shop.repos.RoleRepo;
import com.example.shop.repos.UsersRepo;
import com.example.shop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/shopApi")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsersRepo userRepo;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/register")
    public ResponseEntity<String> autenticateUser (@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User registered-in succesfully.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){

        if(userRepo.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Existe ya", HttpStatus.BAD_REQUEST);
        }

        if(userRepo.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>("Existe ya", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepo.save(user);
        return new ResponseEntity<>("Correcto", HttpStatus.OK);
    }
}
