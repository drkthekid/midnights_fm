package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.AuthLoginDTO;
import com.br.chagas.midnights_fm.dto.request.AuthRegisterDTO;
import com.br.chagas.midnights_fm.dto.response.AuthResponseDTO;
import com.br.chagas.midnights_fm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AuthRegisterDTO authRegisterDTO) {
        authService.registerUser(authRegisterDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDTO login(@RequestBody AuthLoginDTO authLogin) {
        return authService.loginUser(authLogin);
    }

}
