package com.br.chagas.midnights_fm.database.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "single")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "track_id")
    private TrackEntity trackId;

    @NotNull
    // every single needs speak the genre 
    private String genre;

}
