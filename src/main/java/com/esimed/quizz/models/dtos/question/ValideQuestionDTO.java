package com.esimed.quizz.models.dtos.question;

import com.esimed.quizz.models.dtos.score.ScoreDTO;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValideQuestionDTO {
    private boolean success;
    private String bonneReponse;
    private ScoreDTO score;
}
