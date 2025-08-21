package com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.DTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketCommentDTO {
    private Integer id;

    private LocalDate date;

    private String comment;

    private Boolean isPublic;

    private Ticket ticket;
}
