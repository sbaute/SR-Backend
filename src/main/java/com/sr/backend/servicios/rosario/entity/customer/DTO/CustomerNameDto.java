package com.serviciosRosario.ServiciosRosario.entity.customer.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerNameDto {
    private Integer id;
    private String name;
}
