package com.serviciosRosario.ServiciosRosario.payload;

public class MessageClient {
    // Mensajes de éxito

    //-User
    public static final String USER_UPDATED_SUCCESS = "User updated successfully";
    public static final String USER_REGISTER_SUCCESS = "User register successfully";
    public static final String USER_DELETE_SUCCESS = "User was deleted";
    public static final String PASSWORD_UPDATED_SUCCESS = "Password updated successfully";

    // Mensajes de error
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String DATABASE_ERROR = "Database error: %s";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";

    // Mensajes de validación
    public static final String INVALID_PASSWORD = "The current password is incorrect";
}
