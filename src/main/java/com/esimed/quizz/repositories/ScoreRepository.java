package com.esimed.quizz.repositories;

import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.entities.Score;
import com.esimed.quizz.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Score findByCategorieAndUser(Categorie categorie, User user);

    List<Score> findAllByCategorie(Categorie categorie);

    Score findByUser(User user);
}
