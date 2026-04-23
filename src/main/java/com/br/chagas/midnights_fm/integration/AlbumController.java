package com.br.chagas.midnights_fm.integration;

import com.br.chagas.midnights_fm.dto.request.AlbumRequestDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumListResponseDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumResponseDTO;
import com.br.chagas.midnights_fm.unit.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public Page<AlbumListResponseDTO> findAllAlbums(@RequestParam int page,
                                                    @RequestParam int size) {
        return albumService.getAlbums(page, size);
    }

    @GetMapping("/me")
    public Page<AlbumResponseDTO> findAllMyAlbums(@AuthenticationPrincipal UserDetails principal,
                                                  @RequestParam int page,
                                                  @RequestParam int size) {
        return albumService.findAllMyAlbums(principal.getUsername(), page, size);
    }

    @GetMapping("/artist/{username}")
    public Page<AlbumResponseDTO> findAlbumByArtist(@PathVariable String username,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        return albumService.findAllAlbumsByArtist(username, page, size);
    }

    @GetMapping("/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumResponseDTO findAlbumById(@PathVariable Integer id) {
        return albumService.findAlbumById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponseDTO createAlbum(@AuthenticationPrincipal UserDetails principal,
                                        @RequestBody AlbumRequestDTO albumRequestDTO) {
        return albumService.createAlbum(albumRequestDTO, principal.getUsername());
    }

    @DeleteMapping("/{albumID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@AuthenticationPrincipal UserDetails principal,
                            @PathVariable Integer id) {
        albumService.deleteAlbum(id, principal.getUsername());
    }
}
