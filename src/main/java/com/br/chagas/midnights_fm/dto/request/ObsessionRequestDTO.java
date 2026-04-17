package com.br.chagas.midnights_fm.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObsessionRequestDTO {
    private String userId;
    private Integer albumId;
    private Integer trackId;
    private String description;
}
