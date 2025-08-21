package com.serviciosRosario.ServiciosRosario.repository;


import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcategory.TicketCategory;
import org.springframework.data.repository.CrudRepository;

public interface TicketCategoryRepository extends CrudRepository<TicketCategory, Integer> {
}
