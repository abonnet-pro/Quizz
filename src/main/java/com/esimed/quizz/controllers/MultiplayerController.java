package com.esimed.quizz.controllers;

import com.esimed.quizz.models.dtos.question.QuestionDTO;
import com.esimed.quizz.models.dtos.question.ReponseQuestionDTO;
import com.esimed.quizz.models.dtos.question.ValideMultiplayerQuestionDTO;
import com.esimed.quizz.models.dtos.question.ValideQuestionDTO;
import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.entities.Question;
import com.esimed.quizz.models.mappers.CategorieMapper;
import com.esimed.quizz.models.mappers.QuestionMapper;
import com.esimed.quizz.repositories.QuestionRepository;
import com.esimed.quizz.services.CategorieService;
import com.esimed.quizz.services.MultiplayerService;
import com.esimed.quizz.services.QuestionService;
import com.esimed.quizz.services.WebSocketSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MultiplayerController {

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MultiplayerService multiplayerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategorieService categorieService;

    @MessageMapping("/search")
    public void sendNotificationTrigger(@Payload String username) {
        webSocketSessionService.setUserSession(username);
        simpMessagingTemplate.convertAndSend("/queue", webSocketSessionService.getExisting());
    }

    @MessageMapping("/find/categorie")
    public void sendNotificationTrigger() {
        Categorie categorie = categorieService.findRandomCategorieAvailable();
        simpMessagingTemplate.convertAndSend("/categorie/found", CategorieMapper.INSTANCE.categorieToDto(categorie));
    }

    @MessageMapping("/quizz/categorie/{categorieId}/user/{userId}")
    public void sendNotificationTrigger(@DestinationVariable Long categorieId, @DestinationVariable Long userId) throws Exception {
        List<QuestionDTO> questions = questionService.getByCategorie(categorieId).stream().map(QuestionMapper.INSTANCE::questionToDto).collect(Collectors.toList());
        simpMessagingTemplate.convertAndSend("/questions/" + userId, questions);
    }

    @MessageMapping("/multiplayer/question/{questionId}/valide")
    public void sendNotificationTrigger(@DestinationVariable Long questionId, @Payload ReponseQuestionDTO reponse) throws Exception {
        ValideMultiplayerQuestionDTO valideQuestionSender = multiplayerService.valideQuestionSender(questionId, reponse);
        simpMessagingTemplate.convertAndSend("/questions/result/" + valideQuestionSender.getUsername(), valideQuestionSender);

        ValideMultiplayerQuestionDTO valideQuestionOther = multiplayerService.valideQuestionOther(questionId, valideQuestionSender, webSocketSessionService.getExisting());
        simpMessagingTemplate.convertAndSend("/questions/result/" + valideQuestionOther.getUsername(), valideQuestionOther);
    }

    @MessageMapping("/multiplayer/question/{questionId}/timeout")
    public void sendNotificationTriggerFalse(@DestinationVariable Long questionId, @Payload ReponseQuestionDTO reponse) throws Exception {
        ValideMultiplayerQuestionDTO valideQuestionSender = multiplayerService.timeOutQuestion(questionId, reponse);
        simpMessagingTemplate.convertAndSend("/questions/timeout/" + valideQuestionSender.getUsername(), valideQuestionSender);
    }
}