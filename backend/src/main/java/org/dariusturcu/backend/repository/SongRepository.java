package org.dariusturcu.backend.repository;

import org.dariusturcu.backend.model.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
