package com.br.chagas.midnights_fm.unit;

import com.br.chagas.midnights_fm.database.entities.RefreshTokenEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.AuthLoginDTO;
import com.br.chagas.midnights_fm.dto.response.AuthResponseDTO;
import com.br.chagas.midnights_fm.infra.ratelimit.RateLimitService;
import com.br.chagas.midnights_fm.infra.security.TokenService;
import com.br.chagas.midnights_fm.service.AuthService;
import com.br.chagas.midnights_fm.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private RateLimitService rateLimitService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginUserSuccessfully() {

        UserEntity user = new UserEntity();
        user.setUsername("davi");
        user.setPassword("encoded-password");

        AuthLoginDTO dto = new AuthLoginDTO("davi", "123456");

        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(rateLimitService.isBlocked("127.0.0.1")).thenReturn(false);

        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null));

        when(tokenService.generateToken(user))
                .thenReturn("fake-jwt-token");

        when(refreshTokenService.create(user))
                .thenReturn(new RefreshTokenEntity());

        AuthResponseDTO response = authService.loginUser(dto);

        assertEquals("fake-jwt-token", response.accessToken());
    }

}
