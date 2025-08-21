package com.serviciosRosario.ServiciosRosario.exceptions.auth;

import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;

public class UnauthorizedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(ErrorType errorType) {
        super(errorType.getMessage());
    }

    public UnauthorizedException(ErrorType errorType, Throwable cause) {
        super(errorType.getMessage(), cause);
    }
}
