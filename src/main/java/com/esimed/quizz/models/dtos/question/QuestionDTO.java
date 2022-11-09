package com.esimed.quizz.models.dtos.question;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String description;
    private String reponse1;
    private String reponse2;
    private String reponse3;
    private String reponse4;
}
