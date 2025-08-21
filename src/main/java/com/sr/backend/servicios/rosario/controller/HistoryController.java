package com.serviciosRosario.ServiciosRosario.controller;

import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.service.ticket.ITicketService;
import com.serviciosRosario.ServiciosRosario.service.history.IHistoryService;
import com.serviciosRosario.ServiciosRosario.entity.history.DTO.HistoryDTO;
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
public class HistoryController {
    @Autowired
    private IHistoryService historyService;

    @Autowired
    private ITicketService ticketService;

    @GetMapping("history")
    public ResponseEntity<?> getAll(){
        try{
            List<History> ticketHistories = historyService.getAll();
            List<HistoryDTO> historyDTOS = ticketHistories.stream().map(
                    ticketHistory -> historyService.convertToDto(ticketHistory)
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(historyDTOS)
                    .build(),
                    HttpStatus.OK);
        }catch (DataAccessException exDT){
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @GetMapping("history/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        try {
            History history = historyService.getById(id);
            if (history == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("History with id " + id + " was not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(historyService.convertToDto(history))
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

    @GetMapping("history/ticket/{id}")
    public ResponseEntity<?> getTickets(@PathVariable Integer id){
        try{
            List<History> ticketHistories = historyService.getTickets(id);
            List<HistoryDTO> historyDTOS = ticketHistories.stream().map(
                    ticketHistory -> historyService.convertToDto(ticketHistory)
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(historyDTOS)
                    .build(),
                    HttpStatus.OK);
        }catch (DataAccessException exDT){
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @GetMapping("history/customer/{id}")
    public ResponseEntity<?> getCustomers(@PathVariable Integer id){
        try{
            List<History> ticketHistories = historyService.getCustomers(id);
            List<HistoryDTO> historyDTOS = ticketHistories.stream().map(
                    ticketHistory -> historyService.convertToDto(ticketHistory)
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(historyDTOS)
                    .build(),
                    HttpStatus.OK);
        }catch (DataAccessException exDT){
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }


    @PostMapping("history")
    public ResponseEntity<?> create(@RequestBody HistoryDTO historyDTO){
        try {
            if (!ticketService.existsById(historyDTO.getTicket().getId())) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("Ticket not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
            History historySaved = historyService.create(historyDTO);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Ticket History saved into the system")
                    .object(historyService.convertToDto(historySaved))
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @PutMapping("history/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody HistoryDTO historyDTO) {
        try {
            if (historyService.existsById(id)) {
                historyDTO.setId(id);
                History historyUpdate = historyService.update(historyDTO);

                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("ticketHistory Updated")
                        .object(historyService.convertToDto(historyUpdate))
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
    @DeleteMapping("history/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            History historyDelete = historyService.getById(id);
            historyService.delete(historyDelete);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Ticket was deleted")
                    .object(historyDelete)
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
