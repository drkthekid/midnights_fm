package com.br.chagas.midnights_fm.dto.request;

import com.br.chagas.midnights_fm.database.entities.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank
    private String username;

    @NotBlank @Email
    private String email;

    private UserRole role;
}
