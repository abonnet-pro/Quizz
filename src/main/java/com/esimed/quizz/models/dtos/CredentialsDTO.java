package com.esimed.quizz.models.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO {
    private String username;
    private String password;
}
