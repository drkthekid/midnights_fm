package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.ObsessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObsessionRepository extends JpaRepository<ObsessionEntity, Integer> {
}
