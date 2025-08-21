package com.serviciosRosario.ServiciosRosario.entity.ticket.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDTORes {
    private Integer id;

    private String customerCode;

    private String customerName;

    private String assignerName;

    private String operatorName;

    private String areaName;

    private String categoryName;

    private String priorityName;

    private String statusName;

    private Double price;

    private LocalDate serviceVisit;

    private LocalDate activationDate;

    private LocalDate lastModified;

    private String address;

    private String lat;

    private String lng;

    private String betweenAddress1;

    private String betweenAddress2;

    private Integer commentsQuantity;

    private Integer historiesQuantity;

    private Connection connection;
}
