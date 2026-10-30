package org.jqassistant.demo.architecture.hexagonal.shared.adapters.primary.rest.exception;

import com.buschmais.jqassistant.plugin.java.annotation.jQASuppress;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@jQASuppress(value = "rest:DTOMustBeLocatedInModelPackage", reason = "For demonstration purposes.", until = "2026-12-31")
public class ErrorDTO {

    private String message;

}
