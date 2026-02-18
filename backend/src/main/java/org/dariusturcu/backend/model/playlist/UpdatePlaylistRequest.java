package org.dariusturcu.backend.model.playlist;

public record UpdatePlaylistRequest(
        String name,
        String color
) {
}
