package com.esimed.quizz.models.dtos.score;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermineQuizzDTO {
    private Long userId;
    private Long categorieId;
    private boolean medailleOr;
    private boolean medailleArgent;
    private boolean medailleBronze;
}
