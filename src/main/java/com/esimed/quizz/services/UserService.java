package com.esimed.quizz.services;

import com.esimed.quizz.models.dtos.CreateUserDTO;
import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(CreateUserDTO user) {
        User newUser = User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();

        return userRepository.save(newUser);
    }
}
