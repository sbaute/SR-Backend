package com.serviciosRosario.ServiciosRosario.controller;

import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTORes;
import com.serviciosRosario.ServiciosRosario.service.ticket.ITicketService;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.payload.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "api/v1")
public class TicketController {

    @Autowired
    private ITicketService ticketService;

    @GetMapping("ticket")
    public ResponseEntity<?> getAll() {
        try {
            List<Ticket> tickets = ticketService.getAll();
            List<TicketDTORes> ticketDTOs = tickets.stream().map(
                    ticket -> ticketService.convertToDtoRes(ticket)
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(ticketDTOs)
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("ticket/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Ticket ticket = ticketService.getById(id);
            if (ticket == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("Ticket with id " + id + " was not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(ticketService.convertToDto(ticket))
                    .build(),
                    HttpStatus.OK);

        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("ticket")
    public ResponseEntity<?> create(@Valid @RequestBody TicketDTO ticketDTO) {
        try {
            Ticket ticketSaved = ticketService.create(ticketDTO, null);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Ticket saved to system")
                    .object(ticketService.convertToDto(ticketSaved))
            .build(),
    HttpStatus.CREATED);
} catch (DataAccessException exDT) {
        return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
        .object(null)
                    .build(),
HttpStatus.METHOD_NOT_ALLOWED);
        }
        }

    @PutMapping("ticket/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TicketDTO ticketDTO) {
        try {
            if (ticketService.existsById(id)) {
                ticketDTO.setId(id);
                Ticket ticketUpdate = ticketService.update(ticketDTO);

                return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Ticket Updated")
                    .object(ticketService.convertToDto(ticketUpdate))
                    .build(),
                    HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(ResponseMessage.builder()
                    .message("The ticket to update was not found")
                    .object(null)
                    .build(),
                    HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @PostMapping("ticket/temporary/{ticketId}")
    public ResponseEntity<?> convertTemporaryClientToPermanent(@PathVariable Integer ticketId) {
        try {
            Customer newCustomer = ticketService.convertTemporaryClientToPermanent(ticketId);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Temporary client converted to permanent client")
                    .object(newCustomer)
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("ticket/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Ticket ticketDelete = ticketService.getById(id);
            ticketService.delete(ticketDelete);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Ticket was deleted")
                    .object(ticketDelete)
                    .build(),
                    HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
