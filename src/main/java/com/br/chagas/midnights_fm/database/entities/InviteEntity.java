package com.br.chagas.midnights_fm.database.entities;

import com.br.chagas.midnights_fm.database.entities.enums.InviteStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "playlist_invites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "resolver_id")
    private UserEntity resolver;

    private InviteStatus status;
}
