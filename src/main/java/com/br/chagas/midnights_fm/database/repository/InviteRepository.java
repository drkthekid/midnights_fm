package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.PlaylistInviteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<PlaylistInviteEntity, Integer> {
}
