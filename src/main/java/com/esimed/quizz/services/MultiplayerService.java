package com.esimed.quizz.services;

import com.esimed.quizz.models.dtos.question.ReponseQuestionDTO;
import com.esimed.quizz.models.dtos.question.ValideMultiplayerQuestionDTO;
import com.esimed.quizz.models.dtos.question.ValideQuestionDTO;
import com.esimed.quizz.models.dtos.score.TermineQuestionDTO;
import com.esimed.quizz.models.entities.Question;
import com.esimed.quizz.models.entities.Score;
import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.models.mappers.ScoreMapper;
import com.esimed.quizz.repositories.QuestionRepository;
import com.esimed.quizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MultiplayerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionService questionService;

    public ValideMultiplayerQuestionDTO valideQuestionSender(Long questionId, ReponseQuestionDTO reponse) throws Exception {
        ValideQuestionDTO valideQuestion = questionService.valideQuestion(questionId, reponse);
        return ValideMultiplayerQuestionDTO.builder()
                .bonneReponse(valideQuestion.getBonneReponse())
                .score(valideQuestion.getScore())
                .success(valideQuestion.isSuccess())
                .reponseSender(reponse.getReponse())
                .repondant(userRepository.findById(reponse.getUserId()).get().getUsername())
                .username(userRepository.findById(reponse.getUserId()).get().getUsername())
                .build();
    }

    public ValideMultiplayerQuestionDTO timeOutQuestion(Long questionId, ReponseQuestionDTO reponse) throws Exception {
        ValideQuestionDTO valideQuestion = questionService.valideQuestion(questionId, reponse);
        return ValideMultiplayerQuestionDTO.builder()
                .bonneReponse(valideQuestion.getBonneReponse())
                .score(valideQuestion.getScore())
                .success(valideQuestion.isSuccess())
                .reponseSender(null)
                .repondant(null)
                .username(userRepository.findById(reponse.getUserId()).get().getUsername())
                .build();
    }


    public ValideMultiplayerQuestionDTO valideQuestionOther(Long questionId, ValideMultiplayerQuestionDTO valideQuestionSender, List<String> players) throws Exception {
        ValideQuestionDTO valideQuestion;
        String otherPlayer = players.stream().filter(p -> !valideQuestionSender.getUsername().equals(p)).collect(Collectors.toList()).get(0);
        User user = userRepository.findByUsername(otherPlayer);
        if(valideQuestionSender.isSuccess()) {
            valideQuestion = questionService.valideQuestion(questionId, ReponseQuestionDTO.builder().reponse("").userId(user.getId()).build());
        } else {
            valideQuestion = questionService.valideQuestion(questionId, ReponseQuestionDTO.builder().reponse(valideQuestionSender.getBonneReponse()).userId(user.getId()).build());
        }

        return ValideMultiplayerQuestionDTO.builder()
                .reponseSender(valideQuestionSender.getReponseSender())
                .bonneReponse(valideQuestion.getBonneReponse())
                .score(valideQuestion.getScore())
                .success(valideQuestion.isSuccess())
                .username(user.getUsername())
                .repondant(valideQuestionSender.getUsername())
                .winner(valideQuestionSender.isSuccess() ? valideQuestionSender.getUsername() : user.getUsername())
                .build();
    }
}
