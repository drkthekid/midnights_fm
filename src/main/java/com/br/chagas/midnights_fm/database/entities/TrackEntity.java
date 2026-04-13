package com.br.chagas.midnights_fm.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tracks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private UserEntity artist;

    @ManyToMany
    @JoinTable(
            name = "track_feats",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> feats;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;
}
