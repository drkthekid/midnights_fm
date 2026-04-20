package com.br.chagas.midnights_fm.dto.request;

import com.br.chagas.midnights_fm.database.entities.enums.InviteStatus;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteRequestDTO {
    private Integer playlistId;
    private String senderId;
    private String resolverId;
    private InviteStatus status;
}
