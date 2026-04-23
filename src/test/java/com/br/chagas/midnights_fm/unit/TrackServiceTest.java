package com.br.chagas.midnights_fm.unit;

import com.br.chagas.midnights_fm.database.entities.*;
import com.br.chagas.midnights_fm.database.repository.*;
import com.br.chagas.midnights_fm.dto.request.TrackRequestDTO;
import com.br.chagas.midnights_fm.dto.response.TrackResponseDTO;
import com.br.chagas.midnights_fm.exception.CustomAcessDeniedException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import com.br.chagas.midnights_fm.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private TrackService trackService;

    // =========================
    // FIND BY ID
    // =========================
    @Test
    void shouldFindTrackById() {

        UserEntity artist = new UserEntity();
        artist.setId("1");
        artist.setUsername("davi");

        TrackEntity track = new TrackEntity();
        track.setId(10);
        track.setName("Music");
        track.setArtist(artist);

        when(trackRepository.findById(10))
                .thenReturn(Optional.of(track));

        TrackResponseDTO response = trackService.findTrackById(10);

        assertEquals(10, response.getId());
        assertEquals("Music", response.getName());
        assertEquals("davi", response.getArtist().username());
    }

    @Test
    void shouldThrowWhenTrackNotFound() {

        when(trackRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> trackService.findTrackById(99));
    }

    // =========================
    // CREATE TRACK
    // =========================
    @Test
    void shouldCreateTrackSuccessfully() {

        UserEntity artist = new UserEntity();
        artist.setId("1");
        artist.setUsername("davi");

        UserEntity feat = new UserEntity();
        feat.setId("2");
        feat.setUsername("featUser");

        TrackRequestDTO dto = new TrackRequestDTO();
        dto.setName("New Song");
        dto.setFeatsId(List.of("2"));

        when(userRepository.findByUsername("davi"))
                .thenReturn(Optional.of(artist));

        when(userRepository.findById("2"))
                .thenReturn(Optional.of(feat));

        when(trackRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        TrackResponseDTO response =
                trackService.createTrack("davi", dto);

        assertEquals("New Song", response.getName());
        assertEquals("davi", response.getArtist().username());
    }

    @Test
    void shouldThrowWhenArtistNotFoundOnCreate() {

        TrackRequestDTO dto = new TrackRequestDTO();

        when(userRepository.findByUsername("davi"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> trackService.createTrack("davi", dto));
    }

    // =========================
    // DELETE TRACK
    // =========================
    @Test
    void shouldDeleteTrackSuccessfully() {

        UserEntity artist = new UserEntity();
        artist.setId("1");

        TrackEntity track = new TrackEntity();
        track.setId(10);
        track.setArtist(artist);

        when(trackRepository.findById(10))
                .thenReturn(Optional.of(track));

        trackService.deleteTrack(10, "1");

        verify(trackRepository, times(1)).delete(track);
    }

    @Test
    void shouldThrowWhenNotOwnerDeleting() {

        UserEntity artist = new UserEntity();
        artist.setId("1");

        TrackEntity track = new TrackEntity();
        track.setArtist(artist);

        when(trackRepository.findById(10))
                .thenReturn(Optional.of(track));

        assertThrows(CustomAcessDeniedException.class,
                () -> trackService.deleteTrack(10, "2"));
    }

    // =========================
    // FIND ALL
    // =========================
    @Test
    void shouldReturnPagedTracks() {

        UserEntity artist = new UserEntity();
        artist.setId("1");
        artist.setUsername("davi");

        TrackEntity track = new TrackEntity();
        track.setId(10);
        track.setName("Song");
        track.setArtist(artist);

        Page<TrackEntity> page =
                new PageImpl<>(List.of(track));

        when(trackRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<TrackResponseDTO> result =
                trackService.findAllTracks(0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals("Song", result.getContent().get(0).getName());
    }
}