package com.esimed.quizz.repositories;

import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByCategorie(Categorie categorie);
}
