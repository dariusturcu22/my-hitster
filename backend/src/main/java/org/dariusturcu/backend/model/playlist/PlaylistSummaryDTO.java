package org.dariusturcu.backend.model.playlist;

import jakarta.validation.constraints.NotNull;

public record PlaylistSummaryDTO(
        @NotNull
        Long id,
        @NotNull
        String name,
        @NotNull
        int songCount
) {
}
