package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.UserChangePasswordRequestDTO;
import com.br.chagas.midnights_fm.dto.request.UserRequestDTO;
import com.br.chagas.midnights_fm.dto.response.UserResponseDTO;
import com.br.chagas.midnights_fm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO findMe(@AuthenticationPrincipal UserDetails principal) {
        return userService.findMe(principal.getUsername());
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateMe(@AuthenticationPrincipal UserDetails principal,
                                    @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateMe(principal.getUsername(), userRequestDTO);
    }

    @PatchMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(@AuthenticationPrincipal UserDetails principal,
                                 @RequestBody UserChangePasswordRequestDTO userChangePasswordRequestDTO) {
        return userService.changePassword(userChangePasswordRequestDTO, principal.getUsername());
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@AuthenticationPrincipal UserDetails principal) {
        userService.deleteAccount(principal.getUsername());
    }
}
