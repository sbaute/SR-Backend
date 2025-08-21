package com.serviciosRosario.ServiciosRosario.repository;

import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoxRepository extends CrudRepository<Box, Integer> {
    List<Box> findByActiveTrue();
}
