package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.RefreshTokenEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.AuthLoginDTO;
import com.br.chagas.midnights_fm.dto.request.AuthRegisterDTO;
import com.br.chagas.midnights_fm.dto.response.AuthResponseDTO;
import com.br.chagas.midnights_fm.exception.BadRequestException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import com.br.chagas.midnights_fm.infra.security.TokenService;
import com.br.chagas.midnights_fm.infra.ratelimit.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final HttpServletRequest request;
    private final RateLimitService rateLimitService;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, TokenService tokenService, RefreshTokenService refreshTokenService, HttpServletRequest request, RateLimitService rateLimitService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.request = request;
        this.rateLimitService = rateLimitService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public AuthResponseDTO loginUser(AuthLoginDTO authLoginDTO) {

        String ip = request.getRemoteAddr();

        if (rateLimitService.isBlocked(ip)) {
            throw new RuntimeException("Too many attempts. Try again later.");
        }

        // create token authentication (username + password)
        var authToken = new UsernamePasswordAuthenticationToken(authLoginDTO.getUsername(),
                authLoginDTO.getPassword());

        try {
            // spring authentication
            var authentication = authenticationManager.authenticate(authToken);

            // fetch user logged, and he transforms in the UserEntity for can be used data him in the system
            var user = (UserEntity) authentication.getPrincipal();

            // reset attempts
            rateLimitService.loginSuccess(ip);

            // generate access token
            var accessToken = tokenService.generateToken(user);

            // create refresh token in database
            RefreshTokenEntity refreshToken = refreshTokenService.create(user);

            log.info("Login success userId={} username={} ip={}",
                    user.getId(),
                    user.getUsername(),
                    ip);

            // return the two tokens
            return new AuthResponseDTO(
                    accessToken,
                    refreshToken.getId()
            );
        } catch (Exception e) {
            rateLimitService.loginFailed(ip);
            log.warn("Login failed username={} ip={}", authLoginDTO.getUsername(), ip);
            throw new RuntimeException("Invalid username or password");
        }
    }

    public AuthResponseDTO refresh(String refreshTokenId) {

        // 1. validate refresh token
        RefreshTokenEntity token = refreshTokenService.validate(refreshTokenId);

        // 2. generate new access token
        String newAccessToken = tokenService.generateToken(token.getUser());

        log.info("Token refresh userId={} refreshTokenId={}",
                token.getUser().getId(),
                refreshTokenId);

        // 3. return new access token
        return new AuthResponseDTO(
                newAccessToken,
                token.getId()
        );
    }

    public String registerUser(AuthRegisterDTO request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Register failed username already exists={}", request.getUsername());
            throw new BadRequestException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Register failed email already exists={}", request.getEmail());
            throw new BadRequestException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        log.info("User registered id={} username={}",
                user.getId(),
                user.getUsername());

        return "User registered with success!";
    }
}
