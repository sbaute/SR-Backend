package com.serviciosRosario.ServiciosRosario.repository;

import com.serviciosRosario.ServiciosRosario.entity.history.History;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Integer> {

    List<History> findByTicketId(Integer ticketId);

    List<History> findByCustomerId(Integer customerId);

}
