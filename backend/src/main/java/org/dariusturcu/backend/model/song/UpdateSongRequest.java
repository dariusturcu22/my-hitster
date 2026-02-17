package org.dariusturcu.backend.model.song;

public record UpdateSongRequest(
        String artist,
        String title,
        int releaseYear,
        String youtubeId,
        String gradientColor1,
        String gradientColor2,
        Flag flag
) {
}
