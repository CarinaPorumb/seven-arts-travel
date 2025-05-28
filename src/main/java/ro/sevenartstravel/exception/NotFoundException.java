package ro.sevenartstravel.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Resource not found!");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String entityType, UUID id) {
        super(entityType + " with id " + id + " not found!");
    }

    public NotFoundException(String entityType, String name) {
        super(entityType + " with name " + name + " not found!");
    }
}