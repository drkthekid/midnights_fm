package com.br.chagas.midnights_fm.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangePasswordRequestDTO {
    private String password;
}
