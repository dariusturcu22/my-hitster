package org.dariusturcu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.repository.PlaylistRepository;
import org.dariusturcu.backend.service.ExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
@Tag(name = "Playlist export management", description = "Manages the export of a playlist to a pdf file")
public class ExportController {
    private final ExportService exportService;

    @Operation(summary = "Generate PDF for playlist songs")
    @GetMapping("/{playlistId}/export")
    public ResponseEntity<byte[]> exportPlaylist(
            @PathVariable Long playlistId
    ) {
        byte[] pdfBytes = exportService.generatePdf(playlistId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                "playlist-" + playlistId + ".pdf"
        );
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
