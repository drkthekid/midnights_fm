package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.RefreshTokenEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.entities.enums.UserRole;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.AuthLoginDTO;
import com.br.chagas.midnights_fm.dto.request.AuthRegisterDTO;
import com.br.chagas.midnights_fm.dto.response.AuthResponseDTO;
import com.br.chagas.midnights_fm.exception.BadRequestException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import com.br.chagas.midnights_fm.infra.security.TokenService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public AuthResponseDTO loginUser(AuthLoginDTO authLoginDTO) {
        // create token authentication (username + password)
        var authToken = new UsernamePasswordAuthenticationToken(authLoginDTO.getUsername(),
                authLoginDTO.getPassword());

        // spring authentication
        var authentication = authenticationManager.authenticate(authToken);

        // fetch user logged, and he transforms in the UserEntity for can be used data him in the system
        var user = (UserEntity) authentication.getPrincipal();

        // generate access token
        var accessToken = tokenService.generateToken(user);

        // create refresh token in database
        RefreshTokenEntity refreshToken = refreshTokenService.create(user);

        // return the two tokens
        return new AuthResponseDTO(
                accessToken,
                refreshToken.getId()
        );
    }

    public AuthResponseDTO refresh(String refreshTokenId) {

        // 1. validate refresh token
        RefreshTokenEntity token = refreshTokenService.validate(refreshTokenId);

        // 2. generate new access token
        String newAccessToken = tokenService.generateToken(token.getUser());

        // 3. return new access token
        return new AuthResponseDTO(
                newAccessToken,
                token.getId()
        );

    }

    public String registerUser(AuthRegisterDTO request) throws BadRequestException {
        UserEntity user = new UserEntity();
        if (!userRepository.existsByUsername(request.getUsername())) {
            user.setUsername(request.getUsername());
        }

        if (!userRepository.existsByEmail(request.getUsername())) {
            user.setEmail(request.getEmail());
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        userRepository.save(user);

        return "User registered with success!";
    }


}
