package com.serviciosRosario.ServiciosRosario.exceptions;

import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorResponse;
import com.serviciosRosario.ServiciosRosario.exceptions.auth.UnauthorizedException;
import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        // Crear un mapa de errores
        Map<String, String> errors = new HashMap<>();

        // Recorrer las violaciones de las restricciones (constraint violations)
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();  // Nombre del campo
            String errorMessage = violation.getMessage();  // Mensaje del error

            // Agregar el error al mapa
            errors.put(fieldName, errorMessage);
        }

        // Convertir el mapa de errores a un String
        String errorDetails = errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        // Obtener la fecha y hora del error
        String formattedDate = LocalDateTime.now().format(formatter);

        // Crear la respuesta de error con los detalles de validación
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Validation failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestampFormatted(formattedDate)
                .details(errorDetails)  // Asignar los errores como un String
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        // Obtención del detalle de la clase y el método
        String details = ex.getMessage();

        // Si se quiere más detalle del stack trace (como la clase y método donde ocurrió el error):
        if (details == null || details.isEmpty()) {
            details = "Clase: " + ex.getStackTrace()[0].getClassName() + ", Método: " + ex.getStackTrace()[0].getMethodName();
        }
        String formattedDate = LocalDateTime.now().format(formatter);
        // Creación de la respuesta de error
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(ex.getType())
                .message(ex.getMessage())
                .status(ex.getStatus())  // Usar el status que pasaste al crear la excepción
                .timestampFormatted(formattedDate)
                .details(ex.getDetails())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        // Detalles generales si no es una CustomException
        String details = "Clase: " + ex.getStackTrace()[0].getClassName() + ", Método: " + ex.getStackTrace()[0].getMethodName();
        String formattedDate = LocalDateTime.now().format(formatter);
        // Respuesta para otros tipos de excepciones
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Ha ocurrido un error inesperado: " + ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestampFormatted(formattedDate)
                .details(details)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())  // 403 Forbidden
                .timestampFormatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}