package org.dariusturcu.backend.service;

import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.exception.ResourceNotFoundException;
import org.dariusturcu.backend.model.playlist.PlaylistDetailDTO;
import org.dariusturcu.backend.model.playlist.PlaylistSummaryDTO;
import org.dariusturcu.backend.model.user.CreateUserRequest;
import org.dariusturcu.backend.model.user.UpdateUserRequest;
import org.dariusturcu.backend.model.user.User;
import org.dariusturcu.backend.model.user.UserDTO;
import org.dariusturcu.backend.repository.PlaylistRepository;
import org.dariusturcu.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;

    public UserDTO getUserById(Long userId) {
        return null;
    }

    public UserDTO createUser(CreateUserRequest request) {
        return null;
    }

    public UserDTO updateUser(Long userId, UpdateUserRequest request) {
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
