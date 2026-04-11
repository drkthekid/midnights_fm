package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    // help for authentication
    Optional<UserEntity> findByUsername(String username);
}
