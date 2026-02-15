package org.dariusturcu.backend.service;


import org.dariusturcu.backend.model.playlist.PlaylistDetailDTO;
import org.dariusturcu.backend.model.playlist.PlaylistSummaryDTO;
import org.dariusturcu.backend.model.user.CreateUserRequest;
import org.dariusturcu.backend.model.user.UpdateUserRequest;
import org.dariusturcu.backend.model.user.UserDetailDTO;
import org.dariusturcu.backend.model.user.UserSummaryDTO;
import org.dariusturcu.backend.repository.PlaylistRepository;
import org.dariusturcu.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;

    public UserDetailDTO getUserById(Long userId) {
        return null;
    }

    public UserSummaryDTO createUser(CreateUserRequest request) {
        return null;
    }

    public UserSummaryDTO updateUser(Long userId, UpdateUserRequest request) {
        return null;
    }

    public void deleteUser(Long userId) {

    }

    public PlaylistDetailDTO createPlaylist(Long userId) {
        return null;
    }

    public List<PlaylistSummaryDTO> getUserPlaylists(Long userId) {
        return null;
    }

    public PlaylistSummaryDTO joinPlaylist(Long userId, Long playlistId) {
        return null;
    }

    public void leavePlaylist(Long userId, Long playlistId) {

    }
}
