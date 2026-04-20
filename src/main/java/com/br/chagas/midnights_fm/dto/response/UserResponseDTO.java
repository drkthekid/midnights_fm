package com.br.chagas.midnights_fm.dto.response;

import com.br.chagas.midnights_fm.database.entities.enums.UserRole;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String id;
    private String username;
    private String email;
    private UserRole role;
}
