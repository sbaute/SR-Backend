package com.serviciosRosario.ServiciosRosario.entity.history.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.enums.history.Action;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryDTO {
    private Integer id;

    private LocalDateTime date;

    private Action action;

    private User user;

    private Ticket ticket;

    private String from;

    private String to;
}
