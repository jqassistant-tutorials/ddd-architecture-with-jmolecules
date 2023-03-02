package org.jqassistant.demo.architecture.hexagonal.shared.adapters.primary.rest.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDTO {

    private String message;

}
