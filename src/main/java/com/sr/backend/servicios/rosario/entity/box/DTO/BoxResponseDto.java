package com.serviciosRosario.ServiciosRosario.entity.box.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoxResponseDto {
    private Integer id;
    private String boxNumber;
    private Integer portQuantity;
    //private Integer portPosition;
    private Integer usePort;
    private Integer aviablePort;
    private String comments;
    private Double power;
    private String address;
}
