package org.dariusturcu.backend.model.mapper;

import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.model.playlist.Playlist;
import org.dariusturcu.backend.model.playlist.PlaylistDetailDTO;
import org.dariusturcu.backend.model.playlist.PlaylistSummaryDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlaylistMapper {
    private final SongMapper songMapper;
    private final UserMapper userMapper;

    public PlaylistSummaryDTO toSummaryDTO(Playlist playlist) {
        return new PlaylistSummaryDTO(
                playlist.getId(),
                playlist.getName()
        );
    }

    public PlaylistDetailDTO toDetailDTO(Playlist playlist) {
        return new PlaylistDetailDTO(
                playlist.getId(),
                playlist.getName(),
                playlist.getSongs().stream()
                        .map(songMapper::toDTO)
                        .toList(),
                playlist.getUsers().stream()
                        .map(userMapper::toSummaryDTO)
                        .collect(Collectors.toSet())
        );
    }
}
