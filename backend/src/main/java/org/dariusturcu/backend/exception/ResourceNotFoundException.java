package org.dariusturcu.backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(ResourceType resourceType, Long id) {
        super(resourceType.formatNotFoundMessage(id));
    }
}
