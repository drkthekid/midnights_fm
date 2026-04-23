package com.br.chagas.midnights_fm.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumResponseDTO {

    private Integer id;
    private String name;
    private String genre;
    private List<Integer> tracksId;
    private UserSummaryDTO artist;
    private Double averageAssessment;

}
