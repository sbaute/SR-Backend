package com.serviciosRosario.ServiciosRosario.service.ticketcomment.impl;
import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import com.serviciosRosario.ServiciosRosario.exceptions.CustomException;
import com.serviciosRosario.ServiciosRosario.service.ticketcomment.ITicketCommentService;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.DTO.TicketCommentDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.TicketComment;
import com.serviciosRosario.ServiciosRosario.repository.TicketCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketCommentServiceImpl implements ITicketCommentService {

    @Autowired
    private TicketCommentRepository ticketCommentRepository;

    @Override
    public List<TicketComment> getAll() {
        return (List<TicketComment>) ticketCommentRepository.findAll();
    }

    @Override
    @Transactional
    public TicketComment getById(Integer id) {
        return ticketCommentRepository.findById(id).orElse(null);
    }
    @Override
    @Transactional
    public TicketComment create(TicketCommentDTO ticketCommentDTO) {
        TicketComment ticketComment = new TicketComment();
        ticketComment.setComment(ticketCommentDTO.getComment());
        ticketComment.setDate(ticketCommentDTO.getDate());
        ticketComment.setTicket(ticketCommentDTO.getTicket());

        return ticketCommentRepository.save(ticketComment);
    }

    @Override
    @Transactional
    public TicketComment update(TicketCommentDTO ticketCommentDTO){
        TicketComment ticketComment = null;

        if(ticketCommentDTO.getId() != null && ticketCommentRepository.existsById(ticketCommentDTO.getId())) {
            ticketComment = ticketCommentRepository.findById(ticketCommentDTO.getId())
                    .orElseThrow(() -> new CustomException(
                            ErrorType.TICKET_COMMENT_NOT_FOUND
                    ));
        }

        ticketComment.setComment(ticketCommentDTO.getComment());
        ticketComment.setDate(ticketCommentDTO.getDate());
        ticketComment.setTicket(ticketCommentDTO.getTicket());

        return ticketCommentRepository.save(ticketComment);
    }
    @Override
    @Transactional
    public void delete(TicketComment ticketComment) {
        ticketCommentRepository.delete(ticketComment);
    }

    @Override
    public boolean existsById(Integer id) {
        return ticketCommentRepository.existsById(id);
    }

    @Override
    public TicketCommentDTO convertToDto(TicketComment ticketComment){
        return TicketCommentDTO.builder()
                .id(ticketComment.getId())
                .comment(ticketComment.getComment())
                .date(ticketComment.getDate())
                .ticket(ticketComment.getTicket())
                .build();
    }
}
