package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.AlbumRequestDTO;
import com.br.chagas.midnights_fm.dto.request.TrackRequestDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumResponseDTO;
import com.br.chagas.midnights_fm.dto.response.TrackResponseDTO;
import com.br.chagas.midnights_fm.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;


    @GetMapping("/track/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<TrackResponseDTO> findAll(@PathVariable Integer page, @PathVariable Integer size) {
        return artistService.findAllTracks(page, size);
    }

    @GetMapping("/track/{id}")
    public TrackResponseDTO findTrackById(@PathVariable Integer id) {
        return artistService.findTrackById(id);
    }


    @PostMapping("/track")
    @ResponseStatus(HttpStatus.CREATED)
    public TrackResponseDTO createTrack(@RequestBody TrackRequestDTO trackRequestDTO) {
        return artistService.createTrack(trackRequestDTO);
    }

    @PutMapping("/track/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackResponseDTO updateTrack(@PathVariable Integer id,
                                        @RequestBody TrackRequestDTO trackRequestDTO) {
        return artistService.update(id, trackRequestDTO);
    }

    @DeleteMapping("/track/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTrack(@PathVariable Integer id) {
        return artistService.deleteTrack(id);
    }

    @GetMapping("/album/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<AlbumResponseDTO> findAllAlbums(@PathVariable Integer page, @PathVariable Integer size) {
        return artistService.findAllAlbums(page, size);
    }

    @GetMapping("/album/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumResponseDTO findAlbumById(@PathVariable Integer id) {
        return artistService.findAlbumById(id);
    }

    @PostMapping("/album")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponseDTO createAlbum(@RequestBody AlbumRequestDTO albumRequestDTO) {
        return artistService.createAlbum(albumRequestDTO);
    }

    @DeleteMapping("/album/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Integer id) {
        artistService.deleteAlbum(id);
    }


}
