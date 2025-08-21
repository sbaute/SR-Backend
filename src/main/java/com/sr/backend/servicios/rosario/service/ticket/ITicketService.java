package com.serviciosRosario.ServiciosRosario.service.ticket;

import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTORes;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.enums.history.Action;
import com.serviciosRosario.ServiciosRosario.repository.CustomerRepository;

import java.util.List;

public interface ITicketService {
    List<Ticket> getAll();
    Ticket getById(Integer id);
    Ticket create(TicketDTO ticketDTO, Integer customerId);
    Ticket update(TicketDTO ticketDTO);
    void delete(Ticket ticket);
    boolean existsById(Integer id);
    Customer convertTemporaryClientToPermanent(Integer ticketId);
    TicketDTO convertToDto(Ticket ticket);
    TicketDTORes convertToDtoRes(Ticket ticket);
}
