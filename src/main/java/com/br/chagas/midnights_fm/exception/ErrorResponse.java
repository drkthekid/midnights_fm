package com.br.chagas.midnights_fm.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponse {
    private String message;
    private Integer status;
}
