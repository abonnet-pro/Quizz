package com.esimed.quizz.models.dtos.question;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReponseQuestionDTO {
    public Long userId;
    public String reponse;
}
