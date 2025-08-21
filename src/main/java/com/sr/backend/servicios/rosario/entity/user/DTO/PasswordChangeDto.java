package com.serviciosRosario.ServiciosRosario.entity.user.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordChangeDto {

    //private String password;
    private String newPassword;

}
