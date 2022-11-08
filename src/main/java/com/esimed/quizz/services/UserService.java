package com.esimed.quizz.services;

import com.esimed.quizz.models.dtos.CreateUserDTO;
import com.esimed.quizz.models.dtos.CredentialsDTO;
import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashPasswordService hashPasswordService;

    public User createUser(CreateUserDTO user) throws Exception {

        verifieInformations(user);

        User newUser = User.builder()
                .email(user.getEmail())
                .password(hashPasswordService.hashPassword(user.getPassword()))
                .username(user.getUsername())
                .build();

        return userRepository.save(newUser);
    }

    public User authenticate(CredentialsDTO credentials) throws Exception {
        User user = userRepository.findByUsername(credentials.getUsername());
        if(user == null) {
            throw new Exception("Informations invalides");
        }
        if(!hashPasswordService.matchPassword(credentials.getPassword(), user.getPassword())) {
            throw new Exception("Informations invalides");
        }

        return user;
    }

    private void verifieInformations(CreateUserDTO user) throws Exception {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Nom d'utilisateur déjà utilisé");
        }

        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email déjà utilisé");
        }
    }
}
