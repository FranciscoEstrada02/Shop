package com.example.shop.services;

import com.example.shop.model.Users;
import com.example.shop.repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users registerUsers(Users users) {
        if (existsUser(users.getName())) {
            throw new NombreUsuarioExistenteException("El nombre de usuario ya está en uso.");
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        return usersRepo.save(users);
    }



    private boolean existsUser(String name){
        return  usersRepo.findByName(name) != null;
    }

    public class NombreUsuarioExistenteException extends RuntimeException {
        public NombreUsuarioExistenteException(String mensaje) {
            super(mensaje);
        }
    }
}
