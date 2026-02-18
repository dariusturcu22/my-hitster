package org.dariusturcu.backend.model.song;

import org.dariusturcu.backend.model.playlist.Playlist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dariusturcu.backend.model.user.User;

@Entity
@Getter
@Setter
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artist;

    private String title;

    private int releaseYear;

    private String youtubeId;

    private String gradientColor1;

    private String gradientColor2;

    @Enumerated(EnumType.STRING)
    private SongTag songTag;

    @Enumerated(EnumType.STRING)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "added_by", nullable = false)
    private User addedBy;
}
