package com.br.chagas.midnights_fm.dto.response;

import com.br.chagas.midnights_fm.database.entities.enums.InviteStatus;

public class PlaylistInviteResponseDTO {
    private Integer id;
    private Integer playlistId;
    private String senderId;
    private String resolverId;
    private InviteStatus status;
}
