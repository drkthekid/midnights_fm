package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Integer> {
}
