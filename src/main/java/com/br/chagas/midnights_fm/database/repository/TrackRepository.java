package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.TrackEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<TrackEntity, Integer> {
    Page<TrackEntity> findAllTracksByArtistUsername(String username, Pageable pageable);

    Page<TrackEntity> findAllTracksByArtist(UserEntity user, Pageable pageable);
}
