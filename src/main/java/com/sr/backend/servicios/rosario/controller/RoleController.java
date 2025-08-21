package com.serviciosRosario.ServiciosRosario.controller;


import com.serviciosRosario.ServiciosRosario.entity.role.Role;
import com.serviciosRosario.ServiciosRosario.payload.ResponseMessage;
import com.serviciosRosario.ServiciosRosario.service.role.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

   /* @GetMapping("/role")
    public ResponseEntity<?> getAll() {
        try {
            List<Role> roles = roleService.getAll();
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(roles)
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }*/

    @GetMapping("/role/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Role role = roleService.getById(id);
            if (role == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The role of id " + id + " was not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(role)
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

    @PostMapping("/role")
    public ResponseEntity<?> create(@RequestBody Role role) {
        try {
            Role roleSave = roleService.create(role);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Role save to system")
                    .object(roleSave)
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
}
