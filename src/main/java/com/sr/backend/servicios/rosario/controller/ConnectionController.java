package com.serviciosRosario.ServiciosRosario.controller;

import com.serviciosRosario.ServiciosRosario.service.connection.impl.ConnectionServiceImpl;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionResponseDto;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionRequestDto;
import com.serviciosRosario.ServiciosRosario.payload.ResponseMessage;
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
public class ConnectionController {

    @Autowired
    private ConnectionServiceImpl connectionService;

    @GetMapping("connection")
    public ResponseEntity<?> getAll() {
        try {
            List<Connection> connections = connectionService.getAll();
            List<ConnectionResponseDto> connectionsDto = connections.stream().map(
                    connection -> {
                        return connectionService.convertToDto(connection);
                    }
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(connectionsDto)
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

    @GetMapping("connection/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Connection connection = connectionService.getById(id);
            if (connection == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The connection of id " + id + " was not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(connectionService.convertToDto(connection))
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

    @PostMapping("connection")
    public ResponseEntity<?> create(@RequestBody ConnectionRequestDto connectionRequestDto) {
        try {
            Connection connectionSave = connectionService.create(connectionRequestDto);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Connection save to system")
                    .object(connectionService.convertToDto(connectionSave))
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

    @PutMapping("connection/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ConnectionRequestDto connectionRequestDto) {
        try {
            if (connectionService.existsById(id)){
                connectionRequestDto.setId(id);
                Connection connectionUpdate = connectionService.update(connectionRequestDto);

                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("Connection update")
                        .object(connectionService.convertToDto(connectionUpdate))
                        .build(),
                        HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The connection who wants to update is not found")
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

    @DeleteMapping("connection/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id){
        try {
            Connection connectionDelete = connectionService.getById(id);
            connectionService.delete(connectionDelete);
            return new ResponseEntity<>(ResponseMessage
                    .builder()
                    .message("The connection was deleted")
                    .object(connectionDelete)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
