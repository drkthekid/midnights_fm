package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.PlaylistEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.PlaylistRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.PlaylistRequestDTO;
import com.br.chagas.midnights_fm.dto.response.PlaylistResponseDTO;
import com.br.chagas.midnights_fm.exception.BadRequestException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public Page<PlaylistResponseDTO> findAllMyPlaylists(String username, Integer page, Integer size) {
        Page<PlaylistEntity> playlist = playlistRepository.findAllPlaylistsByUserUsername(username, PageRequest.of(page, size));

        return playlist.map(p -> PlaylistResponseDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .userId(p.getUser().getId())
                .collaboratorsId(
                        p.getCollaborators() == null
                                ? null
                                : p.getCollaborators().stream().map(c -> c.getId())
                                  .toList()
                ).build());
    }

    public Page<PlaylistResponseDTO> findPlaylistByUserUsername(String username, Integer page, Integer size) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Page<PlaylistEntity> playlist = playlistRepository.findAllPlaylistsByUserUsername(username, PageRequest.of(page, size));

        return playlist.map(p -> PlaylistResponseDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .userId(user.getId())
                .collaboratorsId(
                        p.getCollaborators() == null
                                ? null
                                : p.getCollaborators().stream().map(c -> c.getId())
                                  .toList()
                ).build());
    }

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

    public void deleteMyPlaylist(String username, Integer id) {
        PlaylistEntity playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Playlist not found"));

        if (!playlist.getUser().getUsername().equals(username)) {
            throw new BadRequestException("You not are owner this playlist");
        }

        playlistRepository.delete(playlist);
    }

}
