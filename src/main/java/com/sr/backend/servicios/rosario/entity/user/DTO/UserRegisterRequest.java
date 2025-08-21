package com.serviciosRosario.ServiciosRosario.entity.user.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterRequest {

    private String username;
    private String name;
    private String password;
    private String repeatPassword;
    private String email;
    private String address;
    private String phone;
    private Integer role;

}
