package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.PlaylistRequestDTO;
import com.br.chagas.midnights_fm.dto.response.PlaylistResponseDTO;
import com.br.chagas.midnights_fm.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistResponseDTO createPlaylist(@AuthenticationPrincipal UserDetails principal,
                                              @RequestBody PlaylistRequestDTO playlistRequestDTO) {
        return playlistService.createPlaylist(principal.getUsername(), playlistRequestDTO);
    }

}
