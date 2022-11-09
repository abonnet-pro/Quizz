package com.esimed.quizz.models.dtos.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationsDTO {
    private Long id;
    private String token;
    private String email;
    private String username;
}
