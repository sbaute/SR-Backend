package com.serviciosRosario.ServiciosRosario.entity.user.DTO;


import lombok.Data;

@Data
public class UserRegisterResponse {

    private Integer id;
    private String username;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String role;
    private String jwt;

}
