package org.dariusturcu.backend.model.ai;

public record SongMetadataResponse(
        String title,
        String artist,
        int releaseYear,
        String gradientColor1,
        String gradientColor2
) {

}
