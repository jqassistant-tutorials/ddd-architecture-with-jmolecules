package org.jqassistant.demo.architecture.hexagonal.shared.domain.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
