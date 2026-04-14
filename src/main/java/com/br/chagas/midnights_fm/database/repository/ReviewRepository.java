package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
}
