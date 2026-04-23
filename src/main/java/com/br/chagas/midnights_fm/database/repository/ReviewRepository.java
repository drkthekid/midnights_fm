package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    boolean existsByUserUsernameAndAlbumId(String username, Integer albumId);
}
