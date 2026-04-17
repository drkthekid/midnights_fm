package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.AlbumEntity;
import com.br.chagas.midnights_fm.database.entities.ObsessionEntity;
import com.br.chagas.midnights_fm.database.entities.TrackEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.AlbumRepository;
import com.br.chagas.midnights_fm.database.repository.ObsessionRepository;
import com.br.chagas.midnights_fm.database.repository.TrackRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.ObsessionRequestDTO;
import com.br.chagas.midnights_fm.dto.response.ObsessionResponseDTO;
import com.br.chagas.midnights_fm.exception.BadRequestException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObsessionService {

    private final ObsessionRepository obsessionRepository;
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final UserRepository userRepository;

    public Page<ObsessionResponseDTO> findAllObsession(Integer page, Integer size) {
        Page<ObsessionEntity> obsessions = obsessionRepository.findAll(PageRequest.of(page, size));

        return obsessions.map(o -> ObsessionResponseDTO.builder()
                .id(o.getId())
                .userId(o.getUser().getId())
                .albumId(o.getAlbum() != null ? o.getAlbum().getId() : null)
                .trackId(o.getTrack() != null ? o.getTrack().getId() : null)
                .description(o.getDescription())
                .build());
    }

    public ObsessionResponseDTO createObsession(ObsessionRequestDTO obsessionRequestDTO, String username) {

        TrackEntity track = null;
        AlbumEntity album = null;

        // find track or album
        if (obsessionRequestDTO.getAlbumId() != null) {
            album = albumRepository.findById(obsessionRequestDTO.getAlbumId())
                    .orElseThrow(() -> new NotFoundException("Album not found"));
        }

        if (obsessionRequestDTO.getTrackId() != null) {
            track = trackRepository.findById(obsessionRequestDTO.getTrackId())
                    .orElseThrow(() -> new NotFoundException("Track not found"));
        }

        if (album == null && track == null) {
            throw new BadRequestException("You must provide at least an album or a track");
        }

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        ObsessionEntity obsession = ObsessionEntity.builder()
                .album(album)
                .track(track)
                .description(obsessionRequestDTO.getDescription())
                .user(user)
                .build();

        obsessionRepository.save(obsession);

        return ObsessionResponseDTO.builder()
                .id(obsession.getId())
                .albumId(obsession.getAlbum() != null ? obsession.getAlbum().getId() : null)
                .trackId(obsession.getTrack() != null ? obsession.getTrack().getId() : null)
                .userId(obsession.getUser().getId())
                .description(obsession.getDescription())
                .build();
    }

}
