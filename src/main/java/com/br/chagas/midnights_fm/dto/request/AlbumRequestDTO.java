package com.br.chagas.midnights_fm.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequestDTO {
    private String name;
    private String genre;
    private List<Integer> tracksId;
    private String artistId;
}
