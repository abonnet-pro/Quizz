package com.esimed.quizz.services;

import com.esimed.quizz.models.dtos.categorie.CategorieQuestionsDTO;
import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.repositories.CategorieRepository;
import com.esimed.quizz.repositories.QuestionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Categorie findOne(Long id) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(id);

        if(!categorie.isPresent()) {
            throw new Exception("Categorie invalide");
        }

        return categorie.get();
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public List<Categorie> findAllAvailable() {
        return categorieRepository.findAllAvailable();
    }

    public Categorie findRandomCategorieAvailable() {
        Random random = new Random();
        List<Categorie> categories = categorieRepository.findAllAvailable();
        int randomIndex = random.nextInt(categories.size());
        return categories.get(randomIndex);
    }

    public List<CategorieQuestionsDTO> findAllWithQuestions() {
        return categorieRepository.findAll().stream().map(this::buildCategorieQuestion).collect(Collectors.toList());
    }

    private CategorieQuestionsDTO buildCategorieQuestion(Categorie categorie) {
        return CategorieQuestionsDTO.builder()
                .id(categorie.getId())
                .name(categorie.getName())
                .nbQuestions(questionRepository.findAllByCategorie(categorie).size())
                .build();
    }

    public void deleteCategorie(Long id) throws Exception {
        Optional<Categorie> optCategorie = categorieRepository.findById(id);

        if(!optCategorie.isPresent()) {
            throw new Exception("Categorie invalide");
        }

        categorieRepository.delete(optCategorie.get());
    }

    public Categorie createCategorie(String name) {
        Categorie categorie = Categorie.builder()
                .name(StringUtils.chop(name))
                .build();

        return categorieRepository.save(categorie);
    }
}
