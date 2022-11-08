package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.CreateUserDTO;
import com.esimed.quizz.models.dtos.CredentialsDTO;
import com.esimed.quizz.models.dtos.UserInformationsDTO;
import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.services.JwtService;
import com.esimed.quizz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public UserInformationsDTO signup(@RequestBody CreateUserDTO user) {
        User newUser = userService.createUser(user);
        return UserInformationsDTO.builder()
                .id(newUser.getId())
                .token(jwtService.generateToken(newUser.getUsername()))
                .email(newUser.getEmail())
                .username(newUser.getUsername())
                .build();
    }

    @PostMapping("/signin")
    public UserInformationsDTO signin(@RequestBody CredentialsDTO credentials) throws Exception {
        User user = userService.authenticate(credentials);
        return UserInformationsDTO.builder()
                .id(user.getId())
                .token(jwtService.generateToken(user.getUsername()))
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
