package com.br.chagas.midnights_fm.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangePasswordRequestDTO {

    @NotBlank
    private String password;
}
