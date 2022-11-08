package com.esimed.quizz.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashPasswordService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean matchPassword(String password, String challenge) {
        return bCryptPasswordEncoder.matches(password, challenge);
    }
}
