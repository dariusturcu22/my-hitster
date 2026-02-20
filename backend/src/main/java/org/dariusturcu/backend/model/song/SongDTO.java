package org.dariusturcu.backend.model.song;

import jakarta.validation.constraints.NotNull;
import org.dariusturcu.backend.model.user.UserSummaryDTO;

public record SongDTO(
        @NotNull
        Long id,
        @NotNull
        String artist,
        @NotNull
        String title,
        @NotNull
        int releaseYear,
        @NotNull
        String youtubeId,
        String gradientColor1,
        String gradientColor2,
        SongTag songTag,
        Country country,
        @NotNull
        UserSummaryDTO addedBy
) {
}
