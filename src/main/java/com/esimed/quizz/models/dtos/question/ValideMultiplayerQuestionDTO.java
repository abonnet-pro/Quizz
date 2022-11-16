package com.esimed.quizz.models.dtos.question;

import com.esimed.quizz.models.dtos.score.ScoreDTO;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValideMultiplayerQuestionDTO {
    private boolean success;
    private String bonneReponse;
    private String reponseSender;
    private ScoreDTO score;
    private String username;
    private String repondant;
    private String winner;
}
