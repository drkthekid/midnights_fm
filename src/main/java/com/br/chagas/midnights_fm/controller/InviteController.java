package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.response.InviteResponseDTO;
import com.br.chagas.midnights_fm.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    @GetMapping
    public Page<InviteResponseDTO> findAllInvites(@AuthenticationPrincipal UserDetails principal,
                                                  @PathVariable Integer page, Integer size){
            return inviteService.findAllRequest(principal.getUsername(), page, size);
    }

}
