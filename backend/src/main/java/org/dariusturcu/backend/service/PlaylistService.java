package org.dariusturcu.backend.service;


import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.exception.ResourceNotFoundException;
import org.dariusturcu.backend.exception.ResourceType;
import org.dariusturcu.backend.model.mapper.PlaylistMapper;
import org.dariusturcu.backend.model.mapper.SongMapper;
import org.dariusturcu.backend.model.playlist.Playlist;
import org.dariusturcu.backend.model.playlist.PlaylistDetailDTO;
import org.dariusturcu.backend.model.playlist.UpdatePlaylistRequest;
import org.dariusturcu.backend.model.song.CreateSongRequest;
import org.dariusturcu.backend.model.song.Song;
import org.dariusturcu.backend.model.song.SongDTO;
import org.dariusturcu.backend.model.song.UpdateSongRequest;
import org.dariusturcu.backend.repository.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional

public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final SongMapper songMapper;

    private Playlist findPlaylist(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.PLAYLIST, playlistId));
    }

    private Song findSong(Playlist playlist, Long songId) {
        return playlist.getSongs().stream()
                .filter(song -> Objects.equals(song.getId(), songId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.SONG, songId));
    }

    @Transactional(readOnly = true)
    public PlaylistDetailDTO getPlaylist(
            Long playlistId) {

        Playlist playlist = findPlaylist(playlistId);
        return playlistMapper.toDetailDTO(playlist);
    }

    public PlaylistDetailDTO updatePlaylist(
            Long playlistId,
            UpdatePlaylistRequest request) {

        Playlist playlist = findPlaylist(playlistId);

        if (request.name() != null) {
            playlist.setName(request.name());
            playlistRepository.save(playlist);
        }

        return playlistMapper.toDetailDTO(playlist);
    }

    @Transactional(readOnly = true)
    public SongDTO getSong(
            Long playlistId,
            Long songId) {

        Playlist playlist = findPlaylist(playlistId);

        Song song = findSong(playlist, songId);

        return songMapper.toDTO(song);
    }

    public SongDTO createSong(
            Long playlistId,
            CreateSongRequest request) {

        Playlist playlist = findPlaylist(playlistId);

        Song newSong = songMapper.toEntity(request);

        playlist.addSong(newSong);

        playlistRepository.save(playlist);

        return songMapper.toDTO(newSong);
    }

    public SongDTO updateSong(
            Long playlistId,
            Long songId,
            UpdateSongRequest request) {

        Playlist playlist = findPlaylist(playlistId);

        Song song = findSong(playlist, songId);

        song = songMapper.updateEntity(song, request);

        playlistRepository.save(playlist);

        return songMapper.toDTO(song);
    }

    public void deleteSong(
            Long playlistId,
            Long songId) {

        Playlist playlist = findPlaylist(playlistId);

        Song song = findSong(playlist, songId);

        playlist.removeSong(song);

        playlistRepository.save(playlist);
    }
}
