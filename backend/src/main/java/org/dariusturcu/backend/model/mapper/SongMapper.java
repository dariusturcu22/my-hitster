package org.dariusturcu.backend.model.mapper;

import org.dariusturcu.backend.model.song.*;

import org.dariusturcu.backend.model.user.UserSummaryDTO;
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
                song.getGradientColor2(),
                song.getSongTag() != null ? song.getSongTag() : SongTag.NONE,
                song.getCountry() != null ? song.getCountry() : Country.NONE,
                new UserSummaryDTO(
                        song.getAddedBy().getId(),
                        song.getAddedBy().getUsername()
                )
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
        newSong.setSongTag(request.songTag() != null ? request.songTag() : SongTag.NONE);
        newSong.setCountry(request.country() != null ? request.country() : Country.NONE);

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
        if (request.songTag() != null) {
            song.setSongTag(request.songTag());
        }
        if (request.country() != null) {
            song.setCountry(request.country());
        }
        return song;
    }
}
