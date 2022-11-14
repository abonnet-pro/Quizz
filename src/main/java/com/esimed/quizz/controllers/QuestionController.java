package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.question.CreateQuestionDTO;
import com.esimed.quizz.models.dtos.question.QuestionDTO;
import com.esimed.quizz.models.dtos.question.ReponseQuestionDTO;
import com.esimed.quizz.models.dtos.question.ValideQuestionDTO;
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
    @RolesAllowed("ADMIN")
    public List<QuestionDTO> findAll() {
        return questionService.findAll().stream().map(QuestionMapper.INSTANCE::questionToDto).collect(Collectors.toList());
    }

    @GetMapping("categorie/{id}")
    @RolesAllowed("ADMIN")
    public List<QuestionDTO> findAllByCategorie(@PathVariable("id") Long id) throws Exception {
        return questionService.findAllByCategorie(id).stream().map(QuestionMapper.INSTANCE::questionToDto).collect(Collectors.toList());
    }


    @PostMapping("")
    @RolesAllowed("ADMIN")
    public QuestionDTO create(@RequestBody CreateQuestionDTO question) throws Exception {
        return QuestionMapper.INSTANCE.questionToDto(questionService.create(question));
    }

    @DeleteMapping("{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathVariable("id") Long id) throws Exception {
        questionService.delete(id);
    }

    @PutMapping("{id}")
    @RolesAllowed("ADMIN")
    public QuestionDTO update(@PathVariable("id") Long id, @RequestBody CreateQuestionDTO question) throws Exception {
        return QuestionMapper.INSTANCE.questionToDto(questionService.update(id, question));
    }

    @GetMapping("random/categorie/{id}")
    public List<QuestionDTO> getRandomByCategorie(@PathVariable("id") Long categorieId, @RequestParam("number") int number) throws Exception {
        return questionService.getRandomByCategorie(categorieId, number).stream().map(QuestionMapper.INSTANCE::questionToDto).collect(Collectors.toList());
    }

    @PostMapping("{id}/valide")
    public ValideQuestionDTO valideQuestion(@PathVariable("id") Long id, @RequestBody ReponseQuestionDTO reponse) throws Exception {
        return questionService.valideQuestion(id, reponse);
    }
}
