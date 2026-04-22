package com.br.chagas.midnights_fm.dto.response;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken
) {}
