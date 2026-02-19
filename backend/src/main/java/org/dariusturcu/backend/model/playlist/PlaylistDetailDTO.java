package org.dariusturcu.backend.model.playlist;

import org.dariusturcu.backend.model.song.SongDTO;
import org.dariusturcu.backend.model.user.UserSummaryDTO;

import java.util.List;
import java.util.Set;

public record PlaylistDetailDTO(
        Long id,
        String name,
        String color,
        String inviteLink,
        List<SongDTO> songs,
        Set<UserSummaryDTO> users) {
}
