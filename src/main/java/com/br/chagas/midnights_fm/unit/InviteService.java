package com.br.chagas.midnights_fm.unit;

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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public Page<InviteResponseDTO> findAllRequest(String username, Integer page, Integer size) {
        Page<InviteEntity> invites = inviteRepository
                .findBySenderUsernameOrResolverUsername(username, username, PageRequest.of(page, size));

        return invites.map(i -> InviteResponseDTO.builder()
                .id(i.getId())
                .playlistId(i.getPlaylist().getId())
                .senderId(i.getSender().getId())
                .resolverId(i.getResolver().getId())
                .status(i.getStatus())
                .build()
        );
    }

    public InviteResponseDTO createInvite(String username, InviteRequestDTO inviteRequestDTO) {
        UserEntity sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        PlaylistEntity playlist = playlistRepository.findById(inviteRequestDTO.getPlaylistId())
                .orElseThrow(() -> new NotFoundException("Playlist not found"));

        if (!playlist.getUser().getUsername().equals(username)) {
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

    @Transactional
    public String acceptInvite(String username, Integer id, InviteRequestDTO inviteRequestDTO) {
        // finding invite if exists
        InviteEntity invite = inviteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invite not found"));

        // validation if sender or resolver
        if (!invite.getResolver().getUsername().equals(username)) {
            throw new BadRequestException("You are not the resolver of this invite");
        }

        if (invite.getStatus() != InviteStatus.PENDENT) {
            throw new BadRequestException("Invite already processed");
        }

        // set accept
        invite.setStatus(InviteStatus.ACCEPT);

        // store to the databases
        inviteRepository.save(invite);

        // step 2.

        // finding playlist for id
        PlaylistEntity playlist = invite.getPlaylist();

        // find resolver
        UserEntity resolver = userRepository.findByUsername(username)
                .orElseThrow();

        List<UserEntity> collaborators = playlist.getCollaborators();

        if (!collaborators.contains(resolver)) {
            collaborators.add(resolver);
        }
        playlist.setCollaborators(collaborators);
        playlistRepository.save(playlist);

        return "Invite Accept";

    }

    @Transactional
    public String rejectedInvite(String username, Integer id, InviteRequestDTO inviteRequestDTO) {
        // finding invite if exists
        InviteEntity invite = inviteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invite not found"));

        // validation if sender or resolver
        if (!invite.getResolver().getUsername().equals(username)) {
            throw new BadRequestException("You are not the resolver of this invite");
        }

        if (invite.getStatus() != InviteStatus.PENDENT) {
            throw new BadRequestException("Invite already processed");
        }

        // set rejected
        invite.setStatus(InviteStatus.REJECTED);

        // store to the databases
        inviteRepository.save(invite);

        return "Invite Rejected";

    }
}
