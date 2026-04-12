package com.br.chagas.midnights_fm.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "track")
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

    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @JoinColumn(name = "feat_id")
    private UserEntity featId;
}
