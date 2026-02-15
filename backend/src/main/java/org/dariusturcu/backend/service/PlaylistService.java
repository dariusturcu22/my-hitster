package org.dariusturcu.backend.service;

import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.exception.ResourceNotFoundException;
import org.dariusturcu.backend.model.mapper.PlaylistMapper;
import org.dariusturcu.backend.model.mapper.SongMapper;
import org.dariusturcu.backend.model.mapper.UserMapper;
import org.dariusturcu.backend.model.playlist.Playlist;
import org.dariusturcu.backend.model.playlist.PlaylistDetailDTO;
import org.dariusturcu.backend.model.playlist.UpdatePlaylistRequest;
import org.dariusturcu.backend.model.song.CreateSongRequest;
import org.dariusturcu.backend.model.song.SongDTO;
import org.dariusturcu.backend.model.song.UpdateSongRequest;
import org.dariusturcu.backend.model.user.UserSummaryDTO;
import org.dariusturcu.backend.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final UserMapper userMapper;
    private final SongMapper songMapper;

    public PlaylistDetailDTO getPlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist {id=" + playlistId + "} not found"));
        return playlistMapper.toDetailDTO(playlist);
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
