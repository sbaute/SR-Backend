package com.serviciosRosario.ServiciosRosario.repository;

import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.TicketComment;
import org.springframework.data.repository.CrudRepository;

public interface TicketCommentRepository extends CrudRepository<TicketComment, Integer> {
}
