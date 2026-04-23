package com.br.chagas.midnights_fm.database.repository;

import com.br.chagas.midnights_fm.database.entities.AlbumEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.dto.response.AlbumListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AlbumRepository extends JpaRepository<AlbumEntity, Integer> {
    @Query("""
            SELECT new com.br.chagas.midnights_fm.dto.response.AlbumListResponseDTO(
                a.id,
                a.name,
                a.genre,
                a.artist.username,
                AVG(r.assessment)
            )
            FROM AlbumEntity a
            LEFT JOIN a.reviews r
            GROUP BY a.id, a.name, a.genre, a.artist.username
            """)
    Page<AlbumListResponseDTO> findAllWithAverage(Pageable pageable);
}
