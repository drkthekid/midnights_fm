package com.br.chagas.midnights_fm.dto.response;

public record AlbumListResponseDTO(
        Integer id,
        String name,
        String genre,
        String artistName,
        Double averageAssessment
) {}
