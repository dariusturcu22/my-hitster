package org.dariusturcu.backend.model.mapper;

import org.dariusturcu.backend.model.song.Song;
import org.dariusturcu.backend.model.song.SongDTO;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {
    public SongDTO toDTO(Song song) {
        return new SongDTO(
                song.getId(),
                song.getArtist(),
                song.getTitle(),
                song.getReleaseYear(),
                song.getYoutubeId(),
                song.getGradientColor1(),
                song.getGradientColor2()
        );
    }
}
