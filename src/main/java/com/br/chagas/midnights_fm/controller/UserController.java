package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.UserChangePasswordRequestDTO;
import com.br.chagas.midnights_fm.dto.request.UserRequestDTO;
import com.br.chagas.midnights_fm.dto.response.UserResponseDTO;
import com.br.chagas.midnights_fm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO findMe() {
        return userService.findMe();
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateMe(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateMe(userRequestDTO);
    }

    @PatchMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(@RequestBody UserChangePasswordRequestDTO userChangePasswordRequestDTO) {
        return userService.changePassword(userChangePasswordRequestDTO);
    }
}
