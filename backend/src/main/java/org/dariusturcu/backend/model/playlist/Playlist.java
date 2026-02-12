package org.dariusturcu.backend.model.playlist;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.dariusturcu.backend.model.song.Song;
import org.dariusturcu.backend.model.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<Song> songs = new HashSet<>();

    @ManyToMany(mappedBy = "playlists")
    private Set<User> users = new HashSet<>();
}
