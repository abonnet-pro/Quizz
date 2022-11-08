package com.esimed.quizz.models.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private String email;
    private String password;
    private String username;
}
