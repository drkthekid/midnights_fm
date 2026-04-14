package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<TrackEntity, Integer> {
}
