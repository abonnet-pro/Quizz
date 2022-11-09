package com.esimed.quizz.models.dtos.score;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermineQuestionDTO {
    private Long userId;
    private Long categorieId;
    private boolean success;
}
