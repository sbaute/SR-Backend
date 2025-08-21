package com.serviciosRosario.ServiciosRosario.service.history.impl;

import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import com.serviciosRosario.ServiciosRosario.exceptions.CustomException;
import com.serviciosRosario.ServiciosRosario.service.history.IHistoryService;
import com.serviciosRosario.ServiciosRosario.entity.history.DTO.HistoryDTO;
import com.serviciosRosario.ServiciosRosario.repository.HistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements IHistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<History> getAll(){
        return (List<History>) historyRepository.findAll();
    }
    @Override
    @Transactional
    public History getById(Integer id){
        return historyRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public History create(HistoryDTO historyDTO){
        History history = new History();
        history.setTicket(historyDTO.getTicket());
        history.setDate(historyDTO.getDate());
        history.setAction(historyDTO.getAction());
        history.setFrom(historyDTO.getFrom());
        history.setTo(historyDTO.getTo());
        history.setUser(historyDTO.getUser());
        return historyRepository.save(history);
    }
    @Override
    @Transactional
    public History update(HistoryDTO historyDTO){
        History history = null;
        if(historyDTO.getId() != null && historyRepository.existsById(historyDTO.getId())) {
            history = historyRepository.findById(historyDTO.getId())
                    .orElseThrow(() ->
                            new CustomException(
                                    ErrorType.TICKET_HISTORY_NOT_FOUND
                            ));
        }
        if (historyDTO.getUser() != null) {
            history.setUser(historyDTO.getUser());
        }
        if (historyDTO.getFrom() != null) {
            history.setFrom(historyDTO.getFrom());
        }
        if (historyDTO.getAction() != null) {
            history.setAction(historyDTO.getAction());
        }
        if (historyDTO.getDate() != null) {
            history.setDate(historyDTO.getDate());
        }
        if (historyDTO.getTicket() != null) {
            history.setTicket(historyDTO.getTicket());
        }
        return historyRepository.save(history);
    }

    @Override
    public List<History> getTickets(Integer idTicket){
        return (List<History>) historyRepository.findByTicketId(idTicket);
    }
    @Override
    public List<History> getCustomers(Integer idCustomer){
        return (List<History>) historyRepository.findByCustomerId(idCustomer);
    }

    @Override
    @Transactional
    public void delete(History history){
        historyRepository.delete(history);
    }
    @Override
    public boolean existsById(Integer id){
        return historyRepository.existsById(id);
    }

    @Override
    public HistoryDTO convertToDto(History history){
        return HistoryDTO.builder()
                .id(history.getId())
                .to(history.getTo())
                .from(history.getFrom())
                .action(history.getAction())
                .user(history.getUser())
                .date(history.getDate())
                .ticket(history.getTicket())
                .build();
    }
}
