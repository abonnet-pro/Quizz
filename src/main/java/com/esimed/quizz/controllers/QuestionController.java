package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.question.CreateQuestionDTO;
import com.esimed.quizz.models.entities.Question;
import com.esimed.quizz.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("all")
    public List<Question> findAll() {
        return questionService.findAll();
    }

    @PostMapping("")
    public Question create(@RequestBody CreateQuestionDTO question) throws Exception {
        return questionService.create(question);
    }

    @GetMapping("random/categorie/{id}")
    public List<Question> getRandomByCategorie(@PathVariable("id") Long categorieId, @RequestParam("number") int number) throws Exception {
        return questionService.getRandomByCategorie(categorieId, number);
    }
}
