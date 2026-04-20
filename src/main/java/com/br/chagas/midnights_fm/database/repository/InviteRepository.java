package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.InviteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<InviteEntity, Integer> {
    Page<InviteEntity> findInviteBySenderUsername(String username, Pageable pageable);
}
