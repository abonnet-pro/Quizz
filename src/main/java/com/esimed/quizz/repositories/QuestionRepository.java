package com.esimed.quizz.repositories;

import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByCategorie(Categorie categorie);

    List<Question> findFirst10ByCategorie(Categorie categorie);
}
