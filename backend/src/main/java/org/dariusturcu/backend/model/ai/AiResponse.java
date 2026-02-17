package org.dariusturcu.backend.model.ai;

import java.time.LocalDateTime;

public record AiResponse(
        String content,
        String model,
        long durationMs,
        LocalDateTime timestamp,
        String status
) {
}
