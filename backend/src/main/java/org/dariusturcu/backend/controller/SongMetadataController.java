package org.dariusturcu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.model.ai.AiResponse;
import org.dariusturcu.backend.service.SongMetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
@Tag(name = "Song Metadata", description = "Fetches song metadata from YouTube and music databases")
public class SongMetadataController {
    private final SongMetadataService songMetadataService;

    @Operation(summary = "Fetch song metadata from a YouTube URL")
    @GetMapping("/song")
    public ResponseEntity<AiResponse> getSongMetadata(@RequestParam String youtubeUrl) {
        AiResponse response = songMetadataService.fetchMetadata(youtubeUrl);
        return ResponseEntity.ok(response);
    }
}
