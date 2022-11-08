package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.CreateUserDTO;
import com.esimed.quizz.models.dtos.UserInformationsDTO;
import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public UserInformationsDTO signup(@RequestBody CreateUserDTO user) {
        User newUser = userService.createUser(user);
        return UserInformationsDTO.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .username(newUser.getUsername())
                .build();
    }
}
