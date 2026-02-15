package org.dariusturcu.backend.model.mapper;

import org.dariusturcu.backend.model.song.CreateSongRequest;
import org.dariusturcu.backend.model.song.Song;
import org.dariusturcu.backend.model.song.SongDTO;
import org.dariusturcu.backend.model.song.UpdateSongRequest;

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

    public Song toEntity(CreateSongRequest request) {
        Song newSong = new Song();
        newSong.setArtist(request.artist());
        newSong.setTitle(request.title());
        newSong.setReleaseYear(request.releaseYear());
        newSong.setYoutubeId(request.youtubeId());
        newSong.setGradientColor1(request.gradientColor1());
        newSong.setGradientColor2(request.gradientColor2());
        return newSong;
    }

    public Song updateEntity(Song song, UpdateSongRequest request) {
        if (request.artist() != null) {
            song.setArtist(request.artist());
        }
        if (request.title() != null) {
            song.setTitle(request.title());
        }
        if (request.releaseYear() != 0) {
            song.setReleaseYear(request.releaseYear());
        }
        if (request.youtubeId() != null) {
            song.setYoutubeId(request.youtubeId());
        }
        if (request.gradientColor1() != null) {
            song.setGradientColor1(request.gradientColor1());
        }
        if (request.gradientColor2() != null) {
            song.setGradientColor2(request.gradientColor2());
        }
        return song;
    }
}
