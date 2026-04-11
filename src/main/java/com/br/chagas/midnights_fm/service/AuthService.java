package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.UserRequestDTO;
import com.br.chagas.midnights_fm.dto.response.AuthResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
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

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public AuthResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        // variable fetch register credentials
        var authToken = new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(),
                userRequestDTO.getPassword());

        // spring validation
        var authentication = authenticationManager.authenticate(authToken);

        // fetch user logged, and he transforms in the UserEntity for can be used data him in the system
        var user = (UserEntity) authentication.getPrincipal();

        var token = tokenService.generateToken(user);
        return new AuthResponseDTO(token);
    }


}
