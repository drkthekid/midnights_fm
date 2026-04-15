package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.AlbumEntity;
import com.br.chagas.midnights_fm.database.entities.TrackEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.AlbumRepository;
import com.br.chagas.midnights_fm.database.repository.TrackRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.TrackRequestDTO;
import com.br.chagas.midnights_fm.dto.response.TrackResponseDTO;
import com.br.chagas.midnights_fm.exception.CustomAcessDeniedException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {


    private final TrackRepository trackRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public Page<TrackResponseDTO> findAllTracks(Integer page, Integer size) {
        Page<TrackEntity> tracks = trackRepository.findAll(PageRequest.of(page, size));

        return tracks.map(t -> TrackResponseDTO.builder()
                .id(t.getId())
                .name(t.getName())
                .artistId(t.getArtist().getId())
                .featsId(t.getFeats() != null
                        ? t.getFeats().stream().map(UserEntity::getId).toList()
                        : List.of())
                .albumId(t.getAlbum() != null ? t.getAlbum().getId() : null)
                .build());
    }

    public TrackResponseDTO findTrackById(Integer id) {
        TrackEntity track = trackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Track not found"));

        return TrackResponseDTO.builder()
                .id(track.getId())
                .name(track.getName())
                .artistId(track.getArtist().getId())
                .featsId(track.getFeats() != null
                        ? track.getFeats().stream().map(UserEntity::getId).toList()
                        : List.of())
                .albumId(track.getAlbum() != null ? track.getAlbum().getId() : null)
                .build();
    }

    public TrackResponseDTO createTrack(TrackRequestDTO trackRequestDTO) {
        // list de feats
        List<UserEntity> featsId = new ArrayList<>();

        // validation if artist exists and return exception
        UserEntity artist = userRepository.findById(trackRequestDTO.getArtistId())
                .orElseThrow(() -> new NotFoundException("Artist not found"));

        // find feats
        for (String featId : trackRequestDTO.getFeatsId()) {
            UserEntity feat = userRepository.findById(featId)
                    .orElseThrow(() -> new NotFoundException("Feat not found"));

            featsId.add(feat);
        }

        AlbumEntity album = null;

        // album not null validation (find album in database)
        if (trackRequestDTO.getAlbumId() != null) {
            album = albumRepository.findById(trackRequestDTO.getAlbumId())
                    .orElseThrow(() -> new NotFoundException("Album not found"));
        }


        // create entity
        TrackEntity track = TrackEntity.builder()
                .name(trackRequestDTO.getName())
                .artist(artist)
                .feats(featsId)
                .album(album)
                .build();

        // store to the database
        trackRepository.save(track);

        // response dto
        return TrackResponseDTO.builder()
                .id(track.getId())
                .name(track.getName())
                .artistId(track.getArtist().getId())
                .albumId(track.getAlbum() != null ? track.getAlbum().getId() : null)
                .featsId(track.getFeats().stream()
                        .map(UserEntity::getId)
                        .toList())
                .build();
    }

    public TrackResponseDTO update(Integer id, TrackRequestDTO trackRequestDTO, String username) {
        List<UserEntity> featsId = new ArrayList<>();

        TrackEntity track = trackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Track not found"));

        if (!track.getArtist().getId().equals(username)) {
            throw new CustomAcessDeniedException("You are not the owner of this track!");
        }

        UserEntity newArtist = userRepository.findById(trackRequestDTO.getArtistId())
                .orElseThrow(() -> new NotFoundException("Artist not found"));

        if (trackRequestDTO.getFeatsId() != null) {
            for (String featId : trackRequestDTO.getFeatsId()) {
                if (featId == null) continue;

                UserEntity feat = userRepository.findById(featId)
                        .orElseThrow(() -> new NotFoundException("Feat not found"));

                featsId.add(feat);
            }
        }

        AlbumEntity album = null;
        if (trackRequestDTO.getAlbumId() != null) {
            album = albumRepository.findById(trackRequestDTO.getAlbumId())
                    .orElseThrow(() -> new NotFoundException("Album not found"));
        }

        // name
        if (trackRequestDTO.getName() != null) {
            track.setName(trackRequestDTO.getName());
        }

        // artist
        if (trackRequestDTO.getArtistId() != null) {
            track.setArtist(newArtist);
        }

        // feats
        if (trackRequestDTO.getFeatsId() != null) {
            track.setFeats(featsId);
        }

        // album
        if (trackRequestDTO.getAlbumId() != null) {
            track.setAlbum(album);
        }

        // store to the database
        trackRepository.save(track);

        // return dto
        return TrackResponseDTO.builder()
                .id(track.getId())
                .name(track.getName())
                .artistId(track.getArtist().getId())
                .featsId(track.getFeats() != null
                        ? track.getFeats().stream().map(UserEntity::getId).toList()
                        : List.of())
                .albumId(track.getAlbum() != null ? track.getAlbum().getId() : null)
                .build();
    }

    public void deleteTrack(Integer id, String username) {
        // finding track on database
        TrackEntity track = trackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Track not found"));

        if (!track.getArtist().getId().equals(username)) {
            throw new CustomAcessDeniedException("You are not the owner of this track!");
        }

        // delete track
        trackRepository.delete(track);
    }

}
