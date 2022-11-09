package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.categorie.CategorieDTO;
import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.mappers.CategorieMapper;
import com.esimed.quizz.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categorie")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping("all")
    public List<CategorieDTO> findAll() {
        return categorieService.findAll().stream().map(CategorieMapper.INSTANCE::categorieToDto).collect(Collectors.toList());
    }
}
