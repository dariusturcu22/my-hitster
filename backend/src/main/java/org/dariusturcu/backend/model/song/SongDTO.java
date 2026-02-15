package org.dariusturcu.backend.model.song;

public record SongDTO(
        Long id,
        String artist,
        String title,
        int releaseYear,
        String youtubeId,
        String gradientColor1,
        String gradientColor2) {
}
