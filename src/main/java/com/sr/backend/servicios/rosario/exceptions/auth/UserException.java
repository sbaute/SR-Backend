package com.serviciosRosario.ServiciosRosario.exceptions.auth;


import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException{

    private final String type;
    private final String message;
    private final String details;
    private final int status;

    // Constructor con ErrorType
    public UserException(ErrorType errorType) {
        super(errorType.name());
        this.type = errorType.name();
        this.message = errorType.getMessage();
        this.details = "";
        this.status = errorType.getStatus();
    }
}
