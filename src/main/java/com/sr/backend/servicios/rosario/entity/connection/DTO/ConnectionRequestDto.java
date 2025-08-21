package com.serviciosRosario.ServiciosRosario.entity.connection.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionRequestDto {

    private Integer id;
    private Integer clientId;
    private Integer planId;
    private Integer boxId;
    private String addressIp;
    private Integer portPosition;
}
