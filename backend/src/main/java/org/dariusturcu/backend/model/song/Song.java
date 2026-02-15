package org.dariusturcu.backend.model.song;

import org.dariusturcu.backend.model.playlist.Playlist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;
}
