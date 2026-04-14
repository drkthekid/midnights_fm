package com.br.chagas.midnights_fm.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String commentary;

    private Integer assessment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;
}
