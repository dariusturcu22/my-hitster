package org.dariusturcu.backend.model.user;

import org.dariusturcu.backend.model.playlist.PlaylistSummaryDTO;

import java.util.List;

public record UserDetailDTO(
        Long id,
        String username,
        String email,
        String provider,
        List<PlaylistSummaryDTO> playlists
) {
}
