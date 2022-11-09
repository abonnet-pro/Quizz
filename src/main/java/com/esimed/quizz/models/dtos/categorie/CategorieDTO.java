package com.esimed.quizz.models.dtos.categorie;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorieDTO {
    private Long id;
    private String name;
}
