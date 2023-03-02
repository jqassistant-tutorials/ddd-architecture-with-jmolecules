package org.jqassistant.demo.architecture.hexagonal.shared.adapters.primary.rest.exception;

import org.jqassistant.demo.architecture.hexagonal.shared.domain.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<Object> notFoundException(NotFoundException notFoundException) {
        return status(NOT_FOUND).body(ErrorDTO.builder().message(notFoundException.getMessage()).build());
    }

}
