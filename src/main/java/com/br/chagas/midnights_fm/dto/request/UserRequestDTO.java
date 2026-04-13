package com.br.chagas.midnights_fm.dto.request;

import com.br.chagas.midnights_fm.database.entities.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    private String username;
    private String email;
    private UserRole role;
}
