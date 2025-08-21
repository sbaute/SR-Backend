package com.serviciosRosario.ServiciosRosario.entity.box.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoxRequestDto {
    private Integer id;
    private String boxNumber;
    private Integer portQuantity;
    private String comments;
    private Double power;
    private String address;
}
