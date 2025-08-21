package com.serviciosRosario.ServiciosRosario.service.plan.impl;

import com.serviciosRosario.ServiciosRosario.service.plan.IPlanService;
import com.serviciosRosario.ServiciosRosario.entity.plan.DTO.PlanDto;
import com.serviciosRosario.ServiciosRosario.entity.plan.Plan;
import com.serviciosRosario.ServiciosRosario.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements IPlanService {

    @Autowired
    private PlanRepository planRepository;

    @Override
    public List<Plan> getAll() {
        return (List<Plan>) planRepository.findAll();
    }

    @Override
    public Plan getById(Integer id) {
        return planRepository.findById(id).orElse(null);
    }

    @Override
    public Plan create(PlanDto planDto) {
        Plan plan;
        plan = new Plan();
        plan.setName(planDto.getName());
        plan.setPrice(planDto.getPrice());

        return planRepository.save(plan);
    }

    @Override
    public Plan update(PlanDto planDto) {
        Plan plan = null;
        if(planDto.getId() != null && planRepository.existsById(planDto.getId())){
            plan = planRepository.findById(planDto.getId()).get();
        }
        plan.setName(planDto.getName());
        plan.setPrice(planDto.getPrice());

        return planRepository.save(plan);
    }

    @Override
    public void delete(Plan plan) {
        planRepository.delete(plan);
    }

    @Override
    public boolean existsById(Integer id) {
        return planRepository.existsById(id);
    }

    @Override
    public PlanDto convertToDto(Plan plan){
        return PlanDto.builder()
                .id(plan.getId())
                .name(plan.getName())
                .price(plan.getPrice())
                .build();
    }

}
