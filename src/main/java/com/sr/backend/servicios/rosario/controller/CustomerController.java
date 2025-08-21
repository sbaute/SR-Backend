package com.serviciosRosario.ServiciosRosario.controller;

import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTORes;
import com.serviciosRosario.ServiciosRosario.service.customer.ICustomerService;
import com.serviciosRosario.ServiciosRosario.entity.customer.DTO.CustomerDto;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTO;
import com.serviciosRosario.ServiciosRosario.exceptions.CustomException;
import com.serviciosRosario.ServiciosRosario.payload.ResponseMessage;
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
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("customer")
    public ResponseEntity<?> getAll() {
        try {
            List<Customer> customers = customerService.getAll();
            List<CustomerDto> clientsDto = customers.stream().map(
                    client -> {
                        return customerService.convertToDto(client);
                    }
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(clientsDto)
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

    @GetMapping("customer/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Customer customer = customerService.getById(id);
            if (customer == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The client of id " + id + " was not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(customerService.convertToDto(customer))
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


    @PostMapping("customer")
    public ResponseEntity<?> create(@RequestBody CustomerDto customerDto) {
        try {
            Customer customerSave = customerService.create(customerDto);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Customer saved to system")
                    .object(customerService.convertToDto(customerSave))
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

    @PostMapping("temporaryCustomer")
    public ResponseEntity<?> createTemporary(@RequestBody CustomerDto customerDto) {
        try {
            Customer customerSave = customerService.createTemporary(customerDto);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Temporary Customer saved to system")
                    .object(customerService.convertToDto(customerSave))
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

    @PutMapping("customer/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CustomerDto customerDto) {
        try {
            customerDto.setId(id);
            Customer customerUpdate = customerService.update(customerDto);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Client Update")
                    .object(customerService.convertToDto(customerUpdate))
                    .build(),
                    HttpStatus.CREATED);
        }catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("customer/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id){
        try {
            Customer customerDelete = customerService.getById(id);
            customerService.delete(customerDelete);
            return new ResponseEntity<>(ResponseMessage
                    .builder()
                    .message("The client was deleted")
                    .object(customerDelete)
                    .build(), HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("customer/createConnection/{clientId}")
    public ResponseEntity<?> createConnection(@PathVariable Integer clientId, @RequestBody ConnectionRequestDto connectionRequestDto) {
        try {
            customerService.createConnection(clientId, connectionRequestDto);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Connection add to the Client")
                    .object(null)
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
    @PostMapping("customer/assignTicket/{clientId}")
    public ResponseEntity<?> createTicketForClient(
            @PathVariable Integer clientId,
            @RequestBody TicketDTO ticketDTO) {
        try {
            TicketDTORes createdTicket = customerService.assignTicket(clientId, ticketDTO);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Ticket creado con éxito para el cliente con ID " + clientId)
                    .object(createdTicket)
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CustomException ex) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(ex.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
