package org.dariusturcu.backend.repository;

import org.dariusturcu.backend.model.playlist.Playlist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
