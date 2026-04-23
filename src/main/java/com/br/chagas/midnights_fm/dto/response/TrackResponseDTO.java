package com.br.chagas.midnights_fm.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class TrackResponseDTO {

    private Integer id;
    private String name;
    private UserSummaryDTO artist;
    private List<UserSummaryDTO> feats;
    private Integer albumId;

}
