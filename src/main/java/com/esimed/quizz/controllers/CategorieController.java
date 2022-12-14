package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.categorie.CategorieDTO;
import com.esimed.quizz.models.dtos.categorie.CategorieQuestionsDTO;
import com.esimed.quizz.models.dtos.question.QuestionDTO;
import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.mappers.CategorieMapper;
import com.esimed.quizz.models.mappers.QuestionMapper;
import com.esimed.quizz.services.CategorieService;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    @GetMapping("{id}")
    public CategorieDTO findOne(@PathVariable("id") Long id) throws Exception {
        return CategorieMapper.INSTANCE.categorieToDto(categorieService.findOne(id));
    }

    @GetMapping("questions")
    @RolesAllowed("ADMIN")
    public List<CategorieQuestionsDTO> findAllWithQuestions() {
        return categorieService.findAllWithQuestions();
    }

    @GetMapping("available")
    public List<CategorieDTO> findAllAvailable() {
        return categorieService.findAllAvailable().stream().map(CategorieMapper.INSTANCE::categorieToDto).collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    @RolesAllowed("ADMIN")
    public void deleteCategorie(@PathVariable("id") Long id) throws Exception {
        categorieService.deleteCategorie(id);
    }

    @PostMapping("")
    @RolesAllowed("ADMIN")
    public CategorieDTO createCategorie(@RequestBody String name) {
        return CategorieMapper.INSTANCE.categorieToDto(categorieService.createCategorie(name));
    }


}
