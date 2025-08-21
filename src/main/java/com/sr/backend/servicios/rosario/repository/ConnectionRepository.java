package com.serviciosRosario.ServiciosRosario.repository;


import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Integer> {
}
