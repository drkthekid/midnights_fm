package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.InviteEntity;
import com.br.chagas.midnights_fm.database.entities.PlaylistEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.entities.enums.InviteStatus;
import com.br.chagas.midnights_fm.database.repository.InviteRepository;
import com.br.chagas.midnights_fm.database.repository.PlaylistRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.InviteRequestDTO;
import com.br.chagas.midnights_fm.dto.response.InviteResponseDTO;
import com.br.chagas.midnights_fm.exception.BadRequestException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public Page<InviteResponseDTO> findAllRequest(String username, Integer page, Integer size){

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

            Page<InviteEntity> invites = inviteRepository.findInviteBySenderUsername(username, PageRequest.of(page, size));

            return invites.map(i -> InviteResponseDTO.builder()
                    .id(i.getId())
                    .playlistId(i.getPlaylist().getId())
                    .senderId(user.getId())
                    .resolverId(i.getResolver().getId())
                    .status(i.getStatus())
                    .build()
            );
    }

    public InviteResponseDTO createInvite(String username, InviteRequestDTO inviteRequestDTO){
        UserEntity sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        PlaylistEntity playlist = playlistRepository.findById(inviteRequestDTO.getPlaylistId())
                .orElseThrow(() -> new NotFoundException("Playlist not found"));

        if(!playlist.getUser().getUsername().equals(username)){
            throw new BadRequestException("You not are owner these playlist");
        }

        UserEntity resolver = userRepository.findById(inviteRequestDTO.getResolverId())
                .orElseThrow(() -> new NotFoundException("Resolver not found"));

        InviteEntity invite = InviteEntity.builder()
                .playlist(playlist)
                .resolver(resolver)
                .sender(sender)
                .status(InviteStatus.PENDENT)
                .build();

        inviteRepository.save(invite);

        return InviteResponseDTO.builder()
                .id(invite.getId())
                .playlistId(playlist.getId())
                .resolverId(resolver.getId())
                .senderId(sender.getId())
                .status(invite.getStatus())
                .build();
    }
}
