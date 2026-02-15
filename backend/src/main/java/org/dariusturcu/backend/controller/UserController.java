package org.dariusturcu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.model.playlist.PlaylistDetailDTO;
import org.dariusturcu.backend.model.playlist.PlaylistSummaryDTO;
import org.dariusturcu.backend.model.user.CreateUserRequest;
import org.dariusturcu.backend.model.user.UpdateUserRequest;
import org.dariusturcu.backend.model.user.UserDTO;
import org.dariusturcu.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User management", description = "Handles operations regarding user management")

public class UserController {
    private final UserService userService;

    @Operation(summary = "Get user information")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest request) {
        UserDTO newUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "Update user information")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        UserDTO updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok().body(updatedUser);
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a new playlist for a user")
    @PostMapping("/{userId}/playlists")
    public ResponseEntity<PlaylistDetailDTO> createPlaylist(@PathVariable Long userId) {
        PlaylistDetailDTO newPlaylist = userService.createPlaylist(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlaylist);
    }

    @Operation(summary = "Get the list of all playlists of a user")
    @GetMapping("/{userId}/playlists")
    public ResponseEntity<List<PlaylistSummaryDTO>> getUserPlaylists(@PathVariable Long userId) {
        List<PlaylistSummaryDTO> userPlaylists = userService.getUserPlaylists(userId);
        return ResponseEntity.ok(userPlaylists);
    }

    @Operation(summary = "Join an existing playlist")
    @PostMapping("/{userId}/playlists/{playlistId}")
    public ResponseEntity<PlaylistSummaryDTO> joinPlaylist(@PathVariable Long userId, @PathVariable Long playlistId) {
        PlaylistSummaryDTO joinedPlaylist = userService.joinPlaylist(userId, playlistId);
        return ResponseEntity.ok(joinedPlaylist);
    }

    @Operation(summary = "Leave playlist")
    @DeleteMapping("/{userId}/playlists/{playlistId}")
    public ResponseEntity<Void> leavePlaylist(@PathVariable Long userId, @PathVariable Long playlistId) {
        userService.leavePlaylist(userId, playlistId);
        return ResponseEntity.noContent().build();
    }
}
