package org.jqassistant.demo.architecture.hexagonal.shared.adapters.primary.rest.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UnsuppressedDTO {
    private String message;
}
