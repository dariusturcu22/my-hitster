package org.dariusturcu.backend.model.song;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dariusturcu.backend.model.playlist.Playlist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "songs")
    private Set<Playlist> playlists = new HashSet<>();
}
