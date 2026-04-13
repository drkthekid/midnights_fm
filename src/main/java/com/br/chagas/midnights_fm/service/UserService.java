package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.UserChangePasswordRequestDTO;
import com.br.chagas.midnights_fm.dto.request.UserRequestDTO;
import com.br.chagas.midnights_fm.dto.response.UserResponseDTO;
import com.br.chagas.midnights_fm.exception.BadRequestException;
import com.br.chagas.midnights_fm.exception.ConflictException;
import com.br.chagas.midnights_fm.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO findMe() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity user)) {
            throw new UnauthorizedException("User not authenticated or invalid session");
        }

        return UserResponseDTO.builder()
                .username(user.getEmail())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public UserResponseDTO updateMe(UserRequestDTO dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity user)) {
            throw new UnauthorizedException("User not authenticated or invalid session");
        }

        // EMAIL
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new ConflictException("Email already in use");
            }
            user.setEmail(dto.getEmail());
        }

        // USERNAME
        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new ConflictException("Username already in use");
            }
            user.setUsername(dto.getUsername());
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        userRepository.save(user);

        return UserResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public String changePassword(UserChangePasswordRequestDTO userChangePasswordRequestDTO) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity user)) {
            throw new UnauthorizedException("User not authenticated or invalid session");
        }

        if (userChangePasswordRequestDTO.getPassword().isBlank()) {
            throw new BadRequestException("New password cannot be empty");
        }

        user.setPassword(passwordEncoder.encode(userChangePasswordRequestDTO.getPassword()));
        userRepository.save(user);

        return "Password changed successfully";
    }
}
