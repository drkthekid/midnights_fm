package com.br.chagas.midnights_fm.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private String userId;
    private List<String> collaboratorsId;

}
