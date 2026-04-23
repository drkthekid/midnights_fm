package com.br.chagas.midnights_fm.integration;

import com.br.chagas.midnights_fm.dto.request.PlaylistRequestDTO;
import com.br.chagas.midnights_fm.dto.response.PlaylistResponseDTO;
import com.br.chagas.midnights_fm.unit.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Page<PlaylistResponseDTO> findAllMyPlaylist(@AuthenticationPrincipal UserDetails principal,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return playlistService.findAllMyPlaylists(principal.getUsername(), page, size);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PlaylistResponseDTO> findAllPlaylistByUserUsername(@PathVariable String username,
                                                                   @RequestParam int page,
                                                                   @RequestParam int size) {
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
