package com.br.chagas.midnights_fm.unit;

import com.br.chagas.midnights_fm.database.entities.*;
import com.br.chagas.midnights_fm.database.repository.*;
import com.br.chagas.midnights_fm.dto.request.AlbumRequestDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumListResponseDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumResponseDTO;
import com.br.chagas.midnights_fm.exception.CustomAcessDeniedException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import com.br.chagas.midnights_fm.service.AlbumService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import static org.mockito.Mockito.lenient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private AlbumService albumService;

    // =========================
    // FIND BY ID
    // =========================
    @Test
    void shouldFindAlbumById() {

        UserEntity artist = new UserEntity();
        artist.setId("1");
        artist.setUsername("davi");

        AlbumEntity album = new AlbumEntity();
        album.setId(10);
        album.setName("Album");
        album.setArtist(artist);
        album.setTracks(List.of());

        when(albumRepository.findById(10))
                .thenReturn(Optional.of(album));

        AlbumResponseDTO response = albumService.findAlbumById(10);

        assertEquals(10, response.getId());
        assertEquals("Album", response.getName());
        assertEquals("davi", response.getArtist().username());
    }

    @Test
    void shouldThrowWhenAlbumNotFound() {

        when(albumRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> albumService.findAlbumById(99));
    }

    // =========================
    // CREATE ALBUM
    // =========================
    @Test
    void shouldCreateAlbumSuccessfully() {

        UserEntity artist = new UserEntity();
        artist.setUsername("davi");

        TrackEntity track = new TrackEntity();
        track.setId(1);
        track.setArtist(artist);

        AlbumRequestDTO dto = new AlbumRequestDTO();
        dto.setName("Album");
        dto.setGenre("Rock");
        dto.setTracksId(List.of(1));

        when(userRepository.findByUsername("davi"))
                .thenReturn(Optional.of(artist));

        when(trackRepository.findById(1))
                .thenReturn(Optional.of(track));

        when(albumRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        AlbumResponseDTO response =
                albumService.createAlbum(dto, "davi");

        assertEquals("Album", response.getName());
        assertEquals("Rock", response.getGenre());
    }

    @Test
    void shouldThrowWhenTrackNotOwned() {

        UserEntity artist = new UserEntity();
        artist.setUsername("davi");

        UserEntity otherArtist = new UserEntity();
        otherArtist.setUsername("other");

        TrackEntity track = new TrackEntity();
        track.setId(1);
        track.setArtist(otherArtist);

        AlbumRequestDTO dto = new AlbumRequestDTO();
        dto.setName("Album");
        dto.setGenre("Rock");
        dto.setTracksId(List.of(1));

        lenient().when(userRepository.findByUsername("davi"))
                .thenReturn(Optional.of(artist));

        lenient().when(trackRepository.findById(1))
                .thenReturn(Optional.of(track));

        assertThrows(CustomAcessDeniedException.class,
                () -> albumService.createAlbum(dto, "davi"));
    }

    // =========================
    // DELETE
    // =========================
    @Test
    void shouldDeleteAlbumSuccessfully() {

        UserEntity artist = new UserEntity();
        artist.setId("1");

        AlbumEntity album = new AlbumEntity();
        album.setArtist(artist);

        when(albumRepository.findById(10))
                .thenReturn(Optional.of(album));

        albumService.deleteAlbum(10, "1");

        verify(albumRepository, times(1)).delete(album);
    }

    @Test
    void shouldThrowWhenNotOwnerDeletingAlbum() {

        UserEntity artist = new UserEntity();
        artist.setId("1");

        AlbumEntity album = new AlbumEntity();
        album.setArtist(artist);

        when(albumRepository.findById(10))
                .thenReturn(Optional.of(album));

        assertThrows(CustomAcessDeniedException.class,
                () -> albumService.deleteAlbum(10, "2"));
    }

    // =========================
    // LIST PAGED
    // =========================
    @Test
    void shouldReturnPagedAlbums() {

        AlbumListResponseDTO dto = mock(AlbumListResponseDTO.class);

        Page<AlbumListResponseDTO> page =
                new PageImpl<>(List.of(dto));

        when(albumRepository.findAllWithAverage(any(Pageable.class)))
                .thenReturn(page);

        Page<AlbumListResponseDTO> result =
                albumService.getAlbums(0, 10);

        assertEquals(1, result.getTotalElements());
    }
}