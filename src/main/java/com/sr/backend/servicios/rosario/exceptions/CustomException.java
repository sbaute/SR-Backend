package com.serviciosRosario.ServiciosRosario.exceptions;

import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {
    private final String type;
    private final String message;
    private final String details;
    private final int status;

    // Constructor con ErrorType
    public CustomException(ErrorType errorType) {
        super(errorType.name());
        this.type = errorType.name();
        this.message = errorType.getMessage();
        this.details = "";
        this.status = errorType.getStatus();
    }

    // Constructor (details)
    public CustomException(ErrorType errorType, String details) {
        super(errorType.name());
        this.type = errorType.name();
        this.message = errorType.getMessage();
        this.details = details;
        this.status = errorType.getStatus();
    }

    // Constructor con mensaje y estado personalizado (para lo q ya estaban VER DSP)
    public CustomException(ErrorType errorType, String message, int status) {
        super(errorType.name());
        this.type = errorType.name();
        this.message = message;
        this.details = "";
        this.status = status;
    }
}
