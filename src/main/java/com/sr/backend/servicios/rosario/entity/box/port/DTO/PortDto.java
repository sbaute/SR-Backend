package com.serviciosRosario.ServiciosRosario.entity.box.port.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortDto {

    private Integer id;
    private Integer position;
    private Boolean active;

}
