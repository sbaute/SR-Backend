package com.serviciosRosario.ServiciosRosario.service.customer;

import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.customer.DTO.CustomerDto;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTORes;

import java.time.LocalDateTime;
import java.util.List;

public interface ICustomerService {

    List<Customer> getAll();
    Customer getById(Integer id);
    Customer create(CustomerDto customerDto);
    Customer createTemporary(CustomerDto customerDto);
    Customer update(CustomerDto customerDto);
    void delete(Customer customer);
    String generateCustomerCode();
    String generateTemporaryCode();
    LocalDateTime decodeTemporaryToDate(String code);
    boolean existsById(Integer id);
    TicketDTORes assignTicket(Integer clientId, TicketDTO ticketDTO);
    CustomerDto convertToDto(Customer customer);
    Connection createConnection(Integer clientId, ConnectionRequestDto connectionRequestDto);
}
