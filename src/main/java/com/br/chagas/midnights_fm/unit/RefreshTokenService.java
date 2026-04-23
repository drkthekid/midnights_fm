package com.br.chagas.midnights_fm.unit;

import com.br.chagas.midnights_fm.database.entities.RefreshTokenEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.RefreshTokenRepository;
import com.br.chagas.midnights_fm.exception.BadRequestException;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    // session controlled by database

    private final RefreshTokenRepository refreshTokenRepository;

    // creating refresh token for user logged
    public RefreshTokenEntity create(UserEntity user) {

        // object refresh with initials data
        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(7)) // duration
                .revoked(false)
                .build();

        return refreshTokenRepository.save(token);
    }

    // validate token
    public RefreshTokenEntity validate(String tokenId) {
        RefreshTokenEntity token = refreshTokenRepository.findById(tokenId)
                .orElseThrow(() -> new NotFoundException("Token not found"));

        // verify if logout clean
        if (token.getRevoked()) {
            throw new BadRequestException("Token revoked");
        }

        // verify if token be expires
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token expired");
        }

        // return token valid
        return token;
    }

    public void revoke(String tokenId){
        // looking for token on database
        RefreshTokenEntity token = refreshTokenRepository.findById(tokenId)
                .orElseThrow();

        // set revoked
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

}
