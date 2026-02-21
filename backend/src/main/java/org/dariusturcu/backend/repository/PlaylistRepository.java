package org.dariusturcu.backend.repository;

import org.dariusturcu.backend.model.playlist.Playlist;

import org.dariusturcu.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findPlaylistsByUsers(User user);

    Optional<Playlist> findPlaylistByInviteCode(String inviteCode);
}
