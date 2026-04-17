package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.repository.InviteRepository;
import com.br.chagas.midnights_fm.database.repository.PlaylistRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistInviteService {

    private final InviteRepository inviteRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
}
