package com.esimed.quizz.repositories;

import com.esimed.quizz.models.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Categorie findByName(String name);

    @Query("select distinct c from Categorie c inner join Question q on c.id = q.categorie.id")
    List<Categorie> findAllAvailable();
}
