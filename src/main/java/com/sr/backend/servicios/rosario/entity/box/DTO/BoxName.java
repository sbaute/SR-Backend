package com.serviciosRosario.ServiciosRosario.entity.box.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoxName {
    private String name;
}
