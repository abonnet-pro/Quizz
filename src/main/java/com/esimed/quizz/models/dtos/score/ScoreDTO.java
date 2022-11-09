package com.esimed.quizz.models.dtos.score;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
    private String username;
    private int score;
    private int nbMedailleOr;
    private int nbMedailleArgent;
    private int nbMedailleBronze;
}
