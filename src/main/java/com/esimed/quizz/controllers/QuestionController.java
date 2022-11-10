package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.question.CreateQuestionDTO;
import com.esimed.quizz.models.dtos.question.QuestionDTO;
import com.esimed.quizz.models.enums.Role;
import com.esimed.quizz.models.mappers.QuestionMapper;
import com.esimed.quizz.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("all")
    public List<QuestionDTO> findAll() {
        return questionService.findAll().stream().map(QuestionMapper.INSTANCE::questionToDto).collect(Collectors.toList());
    }

    @PostMapping("")
    @RolesAllowed("ADMIN")
    public QuestionDTO create(@RequestBody CreateQuestionDTO question) throws Exception {
        return QuestionMapper.INSTANCE.questionToDto(questionService.create(question));
    }

    @GetMapping("random/categorie/{id}")
    public List<QuestionDTO> getRandomByCategorie(@PathVariable("id") Long categorieId, @RequestParam("number") int number) throws Exception {
        return questionService.getRandomByCategorie(categorieId, number).stream().map(QuestionMapper.INSTANCE::questionToDto).collect(Collectors.toList());
    }
}
