package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.TrackRequestDTO;
import com.br.chagas.midnights_fm.dto.response.TrackResponseDTO;
import com.br.chagas.midnights_fm.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<TrackResponseDTO> findAll(@PathVariable Integer page, @PathVariable Integer size) {
        return trackService.findAllTracks(page, size);
    }

    @GetMapping("/{id}")
    public TrackResponseDTO findTrackById(@PathVariable Integer id) {
        return trackService.findTrackById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackResponseDTO createTrack(@RequestBody TrackRequestDTO trackRequestDTO) {
        return trackService.createTrack(trackRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackResponseDTO updateTrack(@AuthenticationPrincipal UserDetails principal,
                                        @PathVariable Integer id,
                                        @RequestBody TrackRequestDTO trackRequestDTO) {
        return trackService.update(id, trackRequestDTO, principal.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@AuthenticationPrincipal UserDetails principal,
                            @PathVariable Integer id) {
        trackService.deleteTrack(id, principal.getUsername());
    }

}
