package com.serviciosRosario.ServiciosRosario.repository;

import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketstatus.TicketStatus;
import org.springframework.data.repository.CrudRepository;

public interface TicketStatusRepository extends CrudRepository<TicketStatus, Integer> {
}
