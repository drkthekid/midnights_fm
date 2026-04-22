package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.PlaylistRequestDTO;
import com.br.chagas.midnights_fm.dto.response.PlaylistResponseDTO;
import com.br.chagas.midnights_fm.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/me/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PlaylistResponseDTO> findAllMyPlaylist(@AuthenticationPrincipal UserDetails principal,
                                                       @PathVariable Integer page, @PathVariable Integer size) {
        return playlistService.findAllMyPlaylists(principal.getUsername(), page, size);
    }

    @GetMapping("/{username}/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PlaylistResponseDTO> findAllPlaylistByUserUsername(@PathVariable String username,
                                                                   @PathVariable Integer page, @PathVariable Integer size) {
        return playlistService.findPlaylistByUserUsername(username, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistResponseDTO createPlaylist(@AuthenticationPrincipal UserDetails principal,
                                              @RequestBody PlaylistRequestDTO playlistRequestDTO) {
        return playlistService.createPlaylist(principal.getUsername(), playlistRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaylist(@AuthenticationPrincipal UserDetails principal,
                               @PathVariable Integer id) {
        playlistService.deleteMyPlaylist(principal.getUsername(), id);
    }

}
