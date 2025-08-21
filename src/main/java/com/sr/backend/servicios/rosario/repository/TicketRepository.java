package com.serviciosRosario.ServiciosRosario.repository;

import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {
}
