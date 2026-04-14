package com.br.chagas.midnights_fm.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDTO {

    private String commentary;
    private Integer assessment;
    private String userId;
    private Integer albumId;
}
