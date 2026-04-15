package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.AlbumRequestDTO;
import com.br.chagas.midnights_fm.dto.response.AlbumResponseDTO;
import com.br.chagas.midnights_fm.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/album")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<AlbumResponseDTO> findAllAlbums(@PathVariable Integer page, @PathVariable Integer size) {
        return albumService.findAllAlbums(page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumResponseDTO findAlbumById(@PathVariable Integer id) {
        return albumService.findAlbumById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponseDTO createAlbum(@RequestBody AlbumRequestDTO albumRequestDTO) {
        return albumService.createAlbum(albumRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Integer id) {
        albumService.deleteAlbum(id);
    }
}
