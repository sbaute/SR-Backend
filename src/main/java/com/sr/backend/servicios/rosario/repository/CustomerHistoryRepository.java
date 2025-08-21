package com.serviciosRosario.ServiciosRosario.repository;

import com.serviciosRosario.ServiciosRosario.entity.customer.customerHistory.CustomerHistory;
import org.springframework.data.repository.CrudRepository;

public interface CustomerHistoryRepository extends CrudRepository<CustomerHistory, Integer> {
}
