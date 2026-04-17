package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.ObsessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObsessionRepository extends JpaRepository<ObsessionEntity, Integer> {
    Page<ObsessionEntity> findObsessionByUserUsername(String username, Pageable pageable);
}
