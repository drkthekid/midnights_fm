package com.br.chagas.midnights_fm.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "obsessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObsessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private TrackEntity track;

    private String description;


}
