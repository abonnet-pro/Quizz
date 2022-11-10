package com.esimed.quizz.repositories.seeder;

import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.models.enums.Role;
import com.esimed.quizz.repositories.CategorieRepository;
import com.esimed.quizz.repositories.UserRepository;
import com.esimed.quizz.services.HashPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategorieDataLoader implements CommandLineRunner {

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public void run(String... args) {
        loadUserData();
    }

    private void loadUserData() {
        if (categorieRepository.count() == 0) {
            initCategories().forEach(this::createCategorie);
        }
    }

    private void createCategorie(String name) {
        Categorie categorie = Categorie.builder()
                .name(name)
                .build();
        categorieRepository.save(categorie);
    }

    private List<String> initCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("Géographie");
        categories.add("Culture générale");
        categories.add("Histoire");
        categories.add("Divers");
        categories.add("Sciences");
        categories.add("Cinéma");
        categories.add("Célébrités");
        categories.add("Télévision");
        categories.add("Art");
        categories.add("Animaux");
        categories.add("Sport");

        return categories;
    }
}