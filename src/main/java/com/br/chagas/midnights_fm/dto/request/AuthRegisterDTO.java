package com.br.chagas.midnights_fm.dto.request;

import com.br.chagas.midnights_fm.database.entities.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRegisterDTO {

    private String username;
    private String password;
    private String email;
    private UserRole role;
}
