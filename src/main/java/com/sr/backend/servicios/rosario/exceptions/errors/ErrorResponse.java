package com.serviciosRosario.ServiciosRosario.exceptions.errors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String type;
    private String message;
    private int status;
    private String timestampFormatted;
    private String details;
}
