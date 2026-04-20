package com.br.chagas.midnights_fm.dto.response;

import com.br.chagas.midnights_fm.database.entities.enums.InviteStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteResponseDTO {
    private Integer id;
    private Integer playlistId;
    private String senderId;
    private String resolverId;
    private InviteStatus status;
}
