package com.serviciosRosario.ServiciosRosario.service.ticketcomment;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.DTO.TicketCommentDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.TicketComment;

import java.util.List;

public interface ITicketCommentService {
    List<TicketComment> getAll();
    TicketComment getById(Integer id);
    TicketComment create(TicketCommentDTO ticketCommentDto);
    TicketComment update(TicketCommentDTO ticketCommentDto);
    void delete(TicketComment ticketComment);
    boolean existsById(Integer id);
    TicketCommentDTO convertToDto(TicketComment ticketComment);
}
