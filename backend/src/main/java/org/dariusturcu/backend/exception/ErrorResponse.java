package org.dariusturcu.backend.exception;

public record ErrorResponse(int status, String message, java.time.LocalDateTime timestamp) {
}
