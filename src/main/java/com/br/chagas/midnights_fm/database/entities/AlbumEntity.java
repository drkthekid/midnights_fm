package com.br.chagas.midnights_fm.database.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String genre;

    @OneToMany(mappedBy = "album")
    private List<TrackEntity> tracks;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private UserEntity artist;

    @OneToMany(mappedBy = "album")
    private List<ReviewEntity> reviews = new ArrayList<>();

}
