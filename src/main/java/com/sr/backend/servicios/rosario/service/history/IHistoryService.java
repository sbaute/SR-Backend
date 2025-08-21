package com.serviciosRosario.ServiciosRosario.service.history;
import com.serviciosRosario.ServiciosRosario.entity.history.DTO.HistoryDTO;
import com.serviciosRosario.ServiciosRosario.entity.history.History;

import java.util.List;

public interface IHistoryService {
    List<History> getAll();
    History getById(Integer id);
    List<History> getTickets(Integer idTicket);
    List<History> getCustomers(Integer idCustomer);
    History create(HistoryDTO historyDTO);
    History update(HistoryDTO historyDTO);
    void delete(History history);
    boolean existsById(Integer id);
    HistoryDTO convertToDto(History history);
}
