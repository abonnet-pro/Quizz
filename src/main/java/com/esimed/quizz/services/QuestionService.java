package com.esimed.quizz.services;

import com.esimed.quizz.models.dtos.question.CreateQuestionDTO;
import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.entities.Question;
import com.esimed.quizz.repositories.CategorieRepository;
import com.esimed.quizz.repositories.QuestionRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question create(CreateQuestionDTO question) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(question.getCategorieId());

        if(categorie.isEmpty()) {
            throw new Exception("Catégorie Invalide");
        }

        Question newQuestion = Question.builder()
                .categorie(categorie.get())
                .description(question.getDescription())
                .reponse1(question.getReponse1())
                .reponse2(question.getReponse2())
                .reponse3(question.getReponse3())
                .reponse4(question.getReponse4())
                .build();

        return questionRepository.save(newQuestion);
    }

    public List<Question> getRandomByCategorie(Long categorieId, int number) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(categorieId);

        if(categorie.isEmpty()) {
            throw new Exception("Catégorie Invalide");
        }

        List<Question> questions = questionRepository.findAllByCategorie(categorie.get());

        if(questions.isEmpty()) {
            throw new Exception("Aucune questions disponibles");
        }

        if(number > questions.size()) {
            throw new Exception("Le nombre de questions demandées est supérieur au nombre de quesitons disponibles");
        }

        Random rand = new Random();
        List<Question> randomQuestions = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int randomIndex = rand.nextInt(questions.size());
            Question randomQuestion = questions.get(randomIndex);
            randomQuestions.add(randomQuestion);
            questions.remove(randomIndex);
        }

        return randomQuestions;
    }
}
