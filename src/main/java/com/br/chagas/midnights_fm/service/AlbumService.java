package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.AlbumEntity;
import com.br.chagas.midnights_fm.database.entities.TrackEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.entities.enums.UserRole;
import com.br.chagas.midnights_fm.database.repository.AlbumRepository;
import com.br.chagas.midnights_fm.database.repository.TrackRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.AlbumRequestDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumListResponseDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumResponseDTO;
import com.br.chagas.midnights_fm.dto.response.UserSummaryDTO;
import com.br.chagas.midnights_fm.exception.BadRequestException;
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
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;

    public Page<AlbumListResponseDTO> getAlbums(Integer page, Integer size) {
        return albumRepository.findAllWithAverage(PageRequest.of(page, size));
    }

    public Page<AlbumResponseDTO> findAllMyAlbums(String username, Integer page, Integer size) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Page<AlbumEntity> albums = albumRepository.findAllAlbumsByArtistUsername(username, PageRequest.of(page, size));

        return albums.map(album -> AlbumResponseDTO.builder()
                .id(album.getId())
                .name(album.getName())
                .genre(album.getGenre())
                .artist(new UserSummaryDTO(
                        album.getArtist().getId(),
                        album.getArtist().getUsername()
                ))
                .tracksId(album.getTracks()
                        .stream()
                        .map(t -> t.getId())
                        .toList())
                .build());

    }

    public Page<AlbumResponseDTO> findAllAlbumsByArtist(String username, Integer page, Integer size) {
        UserEntity artist = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Artist not found"));

        if (artist.getRole() != UserRole.ARTIST) {
            throw new BadRequestException("This user not are an artist");
        }

        Page<AlbumEntity> albums = albumRepository.findAllAlbumsByArtist(artist, PageRequest.of(page, size));

        return albums.map(album -> AlbumResponseDTO.builder()
                .id(album.getId())
                .name(album.getName())
                .genre(album.getGenre())
                .artist(new UserSummaryDTO(
                        artist.getId(),
                        artist.getUsername()
                ))
                .tracksId(album.getTracks()
                        .stream()
                        .map(t -> t.getId())
                        .toList())
                .build());
    }

    public AlbumResponseDTO findAlbumById(Integer id) {
        AlbumEntity album = albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album not found"));

        return AlbumResponseDTO.builder()
                .id(album.getId())
                .name(album.getName())
                .genre(album.getGenre())
                .tracksId(album.getTracks()
                        .stream()
                        .map(t -> t.getId())
                        .toList())
                .artist(new UserSummaryDTO(
                        album.getArtist().getId(),
                        album.getArtist().getUsername()
                ))
                .build();
    }

    public AlbumResponseDTO createAlbum(AlbumRequestDTO albumRequestDTO, String username) {

        List<TrackEntity> tracksIds = new ArrayList<>();

        for (Integer trackId : albumRequestDTO.getTracksId()) {
            TrackEntity track = trackRepository.findById(trackId)
                    .orElseThrow(() -> new NotFoundException("Track not found"));

            if (!track.getArtist().getUsername().equals(username)) {
                throw new CustomAcessDeniedException("You not are owner this track!");
            }

            tracksIds.add(track);
        }

        UserEntity artist = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        AlbumEntity album = AlbumEntity.builder()
                .name(albumRequestDTO.getName())
                .genre(albumRequestDTO.getGenre())
                .artist(artist)
                .tracks(tracksIds)
                .build();

        albumRepository.save(album);

        return AlbumResponseDTO.builder()
                .id(album.getId())
                .name(album.getName())
                .genre(album.getGenre())
                .tracksId(album.getTracks()
                        .stream()
                        .map(track -> track.getId())
                        .toList())
                .artist(new UserSummaryDTO(
                        album.getArtist().getId(),
                        album.getArtist().getUsername()
                ))
                .build();
    }

    public void deleteAlbum(Integer id, String username) {
        AlbumEntity album = albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album not found"));

        if (!album.getArtist().getId().equals(username)) {
            throw new CustomAcessDeniedException("You not are owner this track!");
        }

        albumRepository.delete(album);
    }


}
