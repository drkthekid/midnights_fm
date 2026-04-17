package com.br.chagas.midnights_fm.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObsessionResponseDTO {
    private Integer id;
    private String userId;
    private Integer albumId;
    private Integer trackId;
    private String description;
}
