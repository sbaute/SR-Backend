package com.serviciosRosario.ServiciosRosario.controller;

import com.serviciosRosario.ServiciosRosario.service.box.IBoxService;
import com.serviciosRosario.ServiciosRosario.service.port.IPortService;
import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.box.DTO.BoxRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.box.DTO.BoxResponseDto;
import com.serviciosRosario.ServiciosRosario.entity.box.port.DTO.PortDto;
import com.serviciosRosario.ServiciosRosario.entity.box.port.Port;
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
@RequestMapping("api/v1")
public class BoxController {

    @Autowired
    private IBoxService boxService;

    @Autowired
    private IPortService portService;

    @GetMapping("box")
    public ResponseEntity<?> getAll() {
        try {

            List<Box> boxs = boxService.getAll();

            List<BoxResponseDto> boxsDto = boxs.stream().map(
                    box -> {
                        return boxService.convertToDto(box);
                    }
            ).collect(Collectors.toList());

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(boxsDto)
                    .build(),
                    HttpStatus.OK);

        }catch (DataAccessException exDT){
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);

        }

    }

    @GetMapping("box/active")
    public ResponseEntity<?> getAllActive() {
        try {

            List<Box> boxs = boxService.getAllActive();
            List<BoxResponseDto> boxsDto = boxs.stream().map(
                    box -> {
                        return boxService.convertToDto(box);
                    }
            ).collect(Collectors.toList());
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(boxsDto)
                    .build(),
                    HttpStatus.OK);
        }catch (DataAccessException exDT){
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

    @GetMapping("box/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        try{
            Box box = boxService.getById(id);
            if(box == null){
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The box of id " + id + " was not found")
                        .object(null)
                        .build(),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(boxService.convertToDto(box))
                    .build(),
                    HttpStatus.OK);

        } catch (DataAccessException exDT){
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);

        }

    }

    @PostMapping("box")
    public ResponseEntity<?> create (@RequestBody BoxRequestDto boxRequestDto){
        try {
            Box boxSave = boxService.create(boxRequestDto);
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Box save to the system")
                    .object(boxService.convertToDto(boxSave))
                    .build(),
                    HttpStatus.OK);

        }catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }

    }


    @PutMapping("box/{id}")
    public ResponseEntity<?> update (@PathVariable Integer id, @RequestBody BoxRequestDto boxRequestDto) {
        try {
            if (boxService.existsById(id)) {
                boxRequestDto.setId(id);
                Box boxUpdate = boxService.update(boxRequestDto);

                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("Box Update")
                        .object(boxService.convertToDto(boxUpdate))
                        .build(),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("The box who wants to update is not found")
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

        @DeleteMapping("box/{id}")
        public ResponseEntity<?> delete (@PathVariable Integer id){
            try {
                Box boxDelete = boxService.getById(id);
                boxService.delete(boxDelete);
                return new ResponseEntity<>(ResponseMessage
                        .builder()
                        .message("The box was deleted")
                        .object(boxDelete)
                        .build(), HttpStatus.NO_CONTENT);
            } catch (DataAccessException exDT) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message(exDT.getMessage())
                        .object(null)
                        .build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @GetMapping("box/getAviablePorts/{boxId}")
    public ResponseEntity<?> getPortsAviable(@PathVariable Integer boxId) {
        try {
            Box box = boxService.getById(boxId);
            List<Port> portsAviable = portService.getPortsAviable(box.getPorts());

            List<PortDto> portDto = portsAviable.stream().map(
                    port -> {
                        return PortDto.builder()
                                .position(port.getPosition())
                                .build();
                    }
            ).collect(Collectors.toList());

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(portDto)
                    .build(),
                    HttpStatus.OK);

        }catch (DataAccessException exDT){
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);

        }

    }

    @GetMapping("box/getDisablePorts/{boxId}")
    public ResponseEntity<?> getPortsDisable(@PathVariable Integer boxId) {
        try {
            Box box = boxService.getById(boxId);
            List<Port> portsDisable = portService.getPortsDisable(box.getPorts());

            List<PortDto> portDto = portsDisable.stream().map(
                    port -> {
                        return PortDto.builder()
                                .position(port.getPosition())
                                .build();
                    }
            ).collect(Collectors.toList());

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("")
                    .object(portDto)
                    .build(),
                    HttpStatus.OK);

        }catch (DataAccessException exDT){
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);

        }

    }




}
