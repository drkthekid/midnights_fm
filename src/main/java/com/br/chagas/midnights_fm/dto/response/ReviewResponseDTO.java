package com.br.chagas.midnights_fm.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDTO {

    private Integer id;
    private String commentary;
    private Integer assessment;
    private String userId;
    private Integer albumId;

}
