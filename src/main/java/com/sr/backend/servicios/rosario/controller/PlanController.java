package com.serviciosRosario.ServiciosRosario.controller;

import com.serviciosRosario.ServiciosRosario.service.plan.IPlanService;
import com.serviciosRosario.ServiciosRosario.entity.plan.DTO.PlanDto;
import com.serviciosRosario.ServiciosRosario.entity.plan.Plan;
import com.serviciosRosario.ServiciosRosario.payload.ResponseMessage;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "api/v1")
public class PlanController {

    @Autowired
    private IPlanService planService;

    @GetMapping("plan")
    public ResponseEntity<?> getAll() {
        try {
            List<Plan> plans = planService.getAll();
            List<PlanDto> plansDto = plans.stream().map(
                    plan -> {
                        return planService.convertToDto(plan);
                    }
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(plansDto)
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("plan/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Plan plan = planService.getById(id);
            if (plan == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The plan of id " + id + " was not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(planService.convertToDto(plan))
                    .build(),
                    HttpStatus.OK);

        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("plan")
    public ResponseEntity<?> create(@RequestBody PlanDto planDto) {
        try {
            Plan planSave = planService.create(planDto);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Plan save to system")
                    .object(planService.convertToDto(planSave))
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("plan/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PlanDto planDto, ServletRequest servletRequest) {
        try {
            if (planService.existsById(id)) {
                planDto.setId(id);
                Plan planUpdate = planService.update(planDto);

                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("Plan Update")
                        .object(planService.convertToDto(planUpdate))
                        .build(),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The plan who wants to update is not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("plan/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id){
        try {
            Plan planDelete = planService.getById(id);
            planService.delete(planDelete);
            return new ResponseEntity<>(ResponseMessage
                    .builder()
                    .message("The plan was deleted")
                    .object(planDelete)
                    .build(), HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
