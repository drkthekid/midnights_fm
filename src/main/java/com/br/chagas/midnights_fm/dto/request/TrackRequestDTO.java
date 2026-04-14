package com.br.chagas.midnights_fm.dto.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackRequestDTO {
    private String name;
    private String artistId;
    private List<String> featsId;
    private Integer albumId;

}
