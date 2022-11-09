package com.esimed.quizz.repositories.seeder;

import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.models.enums.Role;
import com.esimed.quizz.repositories.UserRepository;
import com.esimed.quizz.services.HashPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashPasswordService hashPasswordService;

    @Override
    public void run(String... args) {
        loadUserData();
    }

    private void loadUserData() {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .role(Role.ADMIN)
                    .username("admin")
                    .email("admin@admin.fr")
                    .password(hashPasswordService.hashPassword("admin"))
                    .build();
            userRepository.save(admin);
        }
    }
}