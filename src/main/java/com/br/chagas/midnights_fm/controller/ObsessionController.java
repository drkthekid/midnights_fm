package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.ObsessionRequestDTO;
import com.br.chagas.midnights_fm.dto.response.ObsessionResponseDTO;
import com.br.chagas.midnights_fm.service.ObsessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/obsessions")
@RequiredArgsConstructor
public class ObsessionController {

    private final ObsessionService obsessionService;

    @GetMapping("/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ObsessionResponseDTO> findAllObsessions(@PathVariable Integer page, @PathVariable Integer size) {
        return obsessionService.findAllObsession(page, size);
    }

    @GetMapping("/me/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ObsessionResponseDTO> findAllMyObsessions(@AuthenticationPrincipal UserDetails principal,
                                                          @PathVariable Integer page, @PathVariable Integer size) {
        return obsessionService.findAllMyObsession(page, size, principal.getUsername());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ObsessionResponseDTO createObsession(@AuthenticationPrincipal UserDetails principal,
                                                @RequestBody ObsessionRequestDTO obsessionRequestDTO) {
        return obsessionService.createObsession(obsessionRequestDTO, principal.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteObsession(@AuthenticationPrincipal UserDetails principal,
                                @PathVariable Integer id){
        obsessionService.deleteObsession(id, principal.getUsername());
    }

}
