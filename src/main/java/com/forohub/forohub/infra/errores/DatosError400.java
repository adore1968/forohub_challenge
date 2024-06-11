package com.forohub.forohub.infra.errores;

import org.springframework.validation.FieldError;

public record DatosError400(String campo, String mensaje) {
    public DatosError400(FieldError fieldError) {
        this(
                fieldError.getField(),
                fieldError.getDefaultMessage()
        );
    }
}
