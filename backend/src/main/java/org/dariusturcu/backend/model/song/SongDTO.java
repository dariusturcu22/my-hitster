package org.dariusturcu.backend.model.song;

import org.dariusturcu.backend.model.user.UserSummaryDTO;

public record SongDTO(
        Long id,
        String artist,
        String title,
        int releaseYear,
        String youtubeId,
        String gradientColor1,
        String gradientColor2,
        SongTag songTag,
        Country country,
        UserSummaryDTO addedBy
) {
}
