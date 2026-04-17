package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.PlaylistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Integer> {
    Page<PlaylistEntity> findAllPlaylistsByUserUsername(String username, Pageable pageable);
}
