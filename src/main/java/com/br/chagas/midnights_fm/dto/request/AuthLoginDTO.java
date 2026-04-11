package com.br.chagas.midnights_fm.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginDTO {

    private String username;
    private String password;
}
