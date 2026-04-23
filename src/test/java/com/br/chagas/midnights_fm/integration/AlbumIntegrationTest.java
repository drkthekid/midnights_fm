package com.br.chagas.midnights_fm.integration;

import com.br.chagas.midnights_fm.config.TestSecurityConfig;
import com.br.chagas.midnights_fm.database.entities.TrackEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.entities.enums.UserRole;
import com.br.chagas.midnights_fm.database.repository.AlbumRepository;
import com.br.chagas.midnights_fm.database.repository.TrackRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.AlbumRequestDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumResponseDTO;
import com.br.chagas.midnights_fm.service.AlbumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@Transactional
class AlbumIntegrationTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    void shouldCreateAlbumInDatabase() {

        UserEntity artist = new UserEntity();
        artist.setUsername("davi");
        artist.setEmail("davi@test.com");
        artist.setPassword("123456");
        artist.setRole(UserRole.ARTIST);

        artist = userRepository.save(artist);

        TrackEntity track = new TrackEntity();
        track.setName("Track 1");
        track.setArtist(artist);
        track = trackRepository.save(track);

        AlbumRequestDTO dto = new AlbumRequestDTO();
        dto.setName("Album Test");
        dto.setGenre("Rock");
        dto.setTracksId(List.of(track.getId()));

        AlbumResponseDTO response =
                albumService.createAlbum(dto, "davi");

        assertNotNull(response.getId());
        assertEquals("Album Test", response.getName());

        assertTrue(albumRepository.findById(response.getId()).isPresent());
    }
}