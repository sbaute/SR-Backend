package com.serviciosRosario.ServiciosRosario.repository;

import com.serviciosRosario.ServiciosRosario.entity.plan.Plan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlanRepository extends CrudRepository<Plan, Integer> {
}
