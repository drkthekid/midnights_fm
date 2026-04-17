package com.br.chagas.midnights_fm.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistRequestDTO {

    private String name;
    private String description;
    private String userId;
    private List<String> collaboratorsId;

}
