package com.esimed.quizz.models.dtos.question;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReponseQuestionDTO {
    private Long userId;
    private String reponse;
}
