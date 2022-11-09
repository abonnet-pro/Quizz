package com.esimed.quizz.models.dtos.user;

import com.esimed.quizz.models.enums.Role;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationsDTO {
    private Long id;
    private Role role;
    private String token;
    private String email;
    private String username;
}
