package com.serviciosRosario.ServiciosRosario.security.DTO;

import com.serviciosRosario.ServiciosRosario.entity.role.DTO.RoleDto;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;
    private String role;
}
