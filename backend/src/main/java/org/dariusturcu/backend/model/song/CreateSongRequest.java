package org.dariusturcu.backend.model.song;

import jakarta.validation.constraints.NotBlank;

public record CreateSongRequest(
        @NotBlank(message = "Artist name is required")
        String artist,

        @NotBlank(message = "Song title is required")
        String title,

        int releaseYear,

        @NotBlank(message = "YouTube link is required")
        String youtubeId,

        @NotBlank(message = "Both gradient colors are required")
        String gradientColor1,

        @NotBlank(message = "Both gradient colors are required")
        String gradientColor2,

        SongTag songTag,

        Country country
) {
}
