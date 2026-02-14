package org.dariusturcu.backend.model.playlist;

import org.dariusturcu.backend.model.song.Song;
import org.dariusturcu.backend.model.user.User;

import java.util.List;
import java.util.Set;

public record PlaylistDetailDTO(Long id,
                                String name,
                                List<Song> songs,
                                Set<User> users) {
}
