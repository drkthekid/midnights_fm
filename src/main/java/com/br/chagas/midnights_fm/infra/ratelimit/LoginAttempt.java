package com.br.chagas.midnights_fm.infra.ratelimit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LoginAttempt {

    // count attempts
    private int count;

    // last attempt
    private LocalDateTime lastAttempt;

    // até quando block
    private LocalDateTime blockedUntil;
}
