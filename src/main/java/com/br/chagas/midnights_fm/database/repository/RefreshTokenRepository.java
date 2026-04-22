package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.RefreshTokenEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
    List<RefreshTokenEntity> findByUser(UserEntity user);
}
