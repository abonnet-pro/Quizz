package com.esimed.quizz.repositories;

import com.esimed.quizz.models.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Categorie findByName(String name);
}
