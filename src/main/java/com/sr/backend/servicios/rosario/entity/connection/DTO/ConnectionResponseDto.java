package com.serviciosRosario.ServiciosRosario.entity.connection.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.serviciosRosario.ServiciosRosario.entity.box.DTO.BoxName;
import com.serviciosRosario.ServiciosRosario.entity.customer.DTO.CustomerNameDto;
import com.serviciosRosario.ServiciosRosario.entity.plan.DTO.PlanNameDto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionResponseDto {

    private Integer id;
    private CustomerNameDto client;
    private PlanNameDto planName;
    private BoxName boxName;
    private Integer portPosition;
    private String addressIp;
}
