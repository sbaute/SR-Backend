package com.serviciosRosario.ServiciosRosario.security.DTO;


import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;
}
