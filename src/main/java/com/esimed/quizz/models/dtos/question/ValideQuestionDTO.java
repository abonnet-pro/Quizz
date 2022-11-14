package com.esimed.quizz.models.dtos.question;

import com.esimed.quizz.models.dtos.score.ScoreDTO;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValideQuestionDTO {
    public boolean success;
    public ScoreDTO score;
}
