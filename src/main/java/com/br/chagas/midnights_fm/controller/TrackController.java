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
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping()
    public Page<TrackResponseDTO> findAll(@RequestParam int page,
                                          @RequestParam int size) {
        return trackService.findAllTracks(page, size);
    }

    @GetMapping("/me")
    public Page<TrackResponseDTO> findAllMyTracks(@AuthenticationPrincipal UserDetails principal,
                                                  @RequestParam int page,
                                                  @RequestParam int size) {
        return trackService.findAllMyTracks(principal.getUsername(), page, size);
    }

    @GetMapping("/artist/{username}")
    public Page<TrackResponseDTO> findAllByUsername(@PathVariable String username,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        return trackService.findAllByUsername(username, page, size);
    }

    @GetMapping("/{id}")
    public TrackResponseDTO findTrackById(@PathVariable Integer id) {
        return trackService.findTrackById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackResponseDTO createTrack(@AuthenticationPrincipal UserDetails principal,
                                        @RequestBody TrackRequestDTO trackRequestDTO) {
        return trackService.createTrack(principal.getUsername(), trackRequestDTO);
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
