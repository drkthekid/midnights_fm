package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.PlaylistEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.PlaylistRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.PlaylistRequestDTO;
import com.br.chagas.midnights_fm.dto.response.PlaylistResponseDTO;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;



    public PlaylistResponseDTO createPlaylist(String username, PlaylistRequestDTO playlistRequestDTO) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        PlaylistEntity playlist = PlaylistEntity.builder()
                .name(playlistRequestDTO.getName())
                .description(playlistRequestDTO.getDescription())
                .user(user)
                .build();

        playlistRepository.save(playlist);

        return PlaylistResponseDTO.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .userId(playlist.getUser().getId())
                .collaboratorsId(
                        playlist.getCollaborators() == null
                                ? null
                                : playlist.getCollaborators().stream()
                                  .map(collaborator -> collaborator.getId())
                                  .toList()
                )
                .build();
    }


}
