package com.esimed.quizz.services;

import com.esimed.quizz.models.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User getByUsername(String username);
}
