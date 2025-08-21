package com.serviciosRosario.ServiciosRosario.controller;

import com.serviciosRosario.ServiciosRosario.service.ticket.ITicketService;
import com.serviciosRosario.ServiciosRosario.service.ticketcomment.ITicketCommentService;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.DTO.TicketCommentDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.TicketComment;
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
public class TicketCommentController {
    @Autowired
    private ITicketCommentService ticketCommentService;
    @Autowired
    private ITicketService ticketService;

    @GetMapping("ticketComment")
    public ResponseEntity<?> getAll(){
        try{
            List<TicketComment> ticketComments = ticketCommentService.getAll();
            List<TicketCommentDTO> ticketCommentDTOS = ticketComments.stream().map(
                    ticketComment -> {
                        return TicketCommentDTO.builder()
                                .id(ticketComment.getId())
                                .comment(ticketComment.getComment())
                                .date(ticketComment.getDate())
                                .ticket(ticketComment.getTicket())
                                .build();
                    }
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(ticketCommentDTOS)
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

    @GetMapping("ticketComment/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        try {
            TicketComment ticketComment = ticketCommentService.getById(id);
            if (ticketComment == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("Ticket with id " + id + " was not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(ticketCommentService.convertToDto(ticketComment))
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
    @PostMapping("ticketComment")
    public ResponseEntity<?> create(@RequestBody TicketCommentDTO ticketCommentDTO){
        try {
            if (!ticketService.existsById(ticketCommentDTO.getTicket().getId())) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("Ticket not found")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
            TicketComment ticketCommentSave = ticketCommentService.create(ticketCommentDTO);

            TicketCommentDTO ticketCommentSaveDto = TicketCommentDTO.builder()
                    .id(ticketCommentSave.getId())
                    .comment(ticketCommentSave.getComment())
                    .date(ticketCommentSave.getDate())
                    .ticket(ticketCommentDTO.getTicket())
                    .build();
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Ticket Comment saved into the system")
                    .object(ticketCommentSaveDto)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("ticketComment/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TicketCommentDTO ticketCommentDTO) {
        try {
            if (ticketCommentService.existsById(id)) {
                ticketCommentDTO.setId(id);
                TicketComment ticketComment = ticketCommentService.update(ticketCommentDTO);

                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("Ticket Comment Updated")
                        .object(ticketCommentService.convertToDto(ticketComment))
                        .build(),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The ticket Comment to update was not found")
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
    @DeleteMapping("ticketComment/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            TicketComment ticketComment = ticketCommentService.getById(id);
            ticketCommentService.delete(ticketComment);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Ticket was deleted")
                    .object(ticketComment)
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
