package com.br.chagas.midnights_fm.database.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "singles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "track_id")
    private TrackEntity track;

    @NotNull
    // every single needs speak the genre
    private String genre;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private UserEntity artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

}
