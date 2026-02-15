package org.dariusturcu.backend.service;

import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.exception.ResourceNotFoundException;
import org.dariusturcu.backend.model.playlist.Playlist;
import org.dariusturcu.backend.model.playlist.PlaylistDetailDTO;
import org.dariusturcu.backend.model.playlist.UpdatePlaylistRequest;
import org.dariusturcu.backend.model.song.CreateSongRequest;
import org.dariusturcu.backend.model.song.SongDTO;
import org.dariusturcu.backend.model.song.UpdateSongRequest;
import org.dariusturcu.backend.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;


    public PlaylistDetailDTO getPlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist {id=" + playlistId + "} not found"));
        return new PlaylistDetailDTO(
                playlist.getId(),
                playlist.getName(),
                playlist.getSongs(),
                playlist.getUsers()
        );
    }

    public PlaylistDetailDTO updatePlaylist(Long playlistId, UpdatePlaylistRequest request) {
        return null;
    }


    public SongDTO getSong(Long playlistId, Long songId) {
        return null;
    }

    public SongDTO createSong(Long playlistId, CreateSongRequest request) {
        return null;
    }

    public SongDTO updateSong(Long playlistId, Long songId, UpdateSongRequest request) {
        return null;
    }

    public void deleteSong(Long playlistId, Long songId) {
    }


}
