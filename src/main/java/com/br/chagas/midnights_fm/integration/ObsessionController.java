package com.br.chagas.midnights_fm.integration;

import com.br.chagas.midnights_fm.dto.request.ObsessionRequestDTO;
import com.br.chagas.midnights_fm.dto.response.ObsessionResponseDTO;
import com.br.chagas.midnights_fm.unit.ObsessionService;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ObsessionResponseDTO> findAllObsessions(@RequestParam int page,
                                                        @RequestParam int size) {
        return obsessionService.findAllObsession(page, size);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Page<ObsessionResponseDTO> findAllMyObsessions(@AuthenticationPrincipal UserDetails principal,
                                                          @RequestParam int page,
                                                          @RequestParam int size) {
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
                                @PathVariable Integer id) {
        obsessionService.deleteObsession(id, principal.getUsername());
    }

}
