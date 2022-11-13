package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.score.ScoreDTO;
import com.esimed.quizz.models.dtos.score.TermineQuestionDTO;
import com.esimed.quizz.models.dtos.score.TermineQuizzDTO;
import com.esimed.quizz.models.entities.Score;
import com.esimed.quizz.models.mappers.ScoreMapper;
import com.esimed.quizz.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("termine/question")
    public ScoreDTO termineQuestion(@RequestBody TermineQuestionDTO reponse) throws Exception {
        return ScoreMapper.INSTANCE.scoreToDto(scoreService.termineQuestion(reponse));
    }

    @PostMapping("termine/quizz")
    public ScoreDTO termineQuizz(@RequestBody TermineQuizzDTO resultat) throws Exception {
        return ScoreMapper.INSTANCE.scoreToDto(scoreService.termineQuizz(resultat));
    }

    @GetMapping("ladder")
    public List<ScoreDTO> getLadder(@RequestParam(name = "categorie", required = false) Long categorieId) throws Exception {
        return scoreService.getLadder(categorieId).stream().map(ScoreMapper.INSTANCE::scoreToDto).collect(Collectors.toList());
    }

    @GetMapping("user/{id}")
    public ScoreDTO getScore(@PathVariable("id") Long id) throws Exception {
        return ScoreMapper.INSTANCE.scoreToDto(scoreService.getScore(id));
    }
}
