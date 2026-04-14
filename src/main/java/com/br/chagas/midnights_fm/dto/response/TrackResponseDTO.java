package com.br.chagas.midnights_fm.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackResponseDTO {

    private Integer id;
    private String name;
    private String artistId;
    private List<String> featsId;
    private Integer albumId;

}
