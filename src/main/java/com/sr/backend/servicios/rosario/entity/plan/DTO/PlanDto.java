package com.serviciosRosario.ServiciosRosario.entity.plan.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanDto {

    private Integer id;
    private String name;
    private Double price;

}
