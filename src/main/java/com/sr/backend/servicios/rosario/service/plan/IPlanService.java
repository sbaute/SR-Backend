package com.serviciosRosario.ServiciosRosario.service.plan;


import com.serviciosRosario.ServiciosRosario.entity.plan.DTO.PlanDto;
import com.serviciosRosario.ServiciosRosario.entity.plan.Plan;

import java.util.List;

public interface IPlanService {

    List<Plan> getAll();
    Plan getById(Integer id);
    Plan create(PlanDto planDto);
    void delete(Plan plan);
    boolean existsById(Integer id);
    Plan update (PlanDto planDto);
    PlanDto convertToDto(Plan plan);
}
