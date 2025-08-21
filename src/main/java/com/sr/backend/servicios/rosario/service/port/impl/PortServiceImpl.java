package com.serviciosRosario.ServiciosRosario.service.port.impl;

import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import com.serviciosRosario.ServiciosRosario.service.port.IPortService;
import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.box.port.Port;
import com.serviciosRosario.ServiciosRosario.exceptions.CustomException;
import com.serviciosRosario.ServiciosRosario.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortServiceImpl implements IPortService {

    @Autowired
    private PortRepository portRepository;

    @Override
    public List<Port> getAll() {
        return List.of();
    }

    @Override
    public Port getById(Integer id) {
        return portRepository.findById(id).orElse(null);
    }

    @Override
    public Port create(Port port) {
        return portRepository.save(port);
    }

    @Override
    public Port update(Port port) {
        return portRepository.save(port);
    }

    @Override
    public void delete(Port port) {
        port.setActive(false);
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void createPositionsOfPorts(Integer portQuantity, Box box) {
        List<Port> ports = new ArrayList<>();

        for (int i = 1; i <= portQuantity; i++) {
            Port port = new Port();
            port.setPosition(i);
            port.setActive(false);
            port.setBox(box);
            ports.add(port);
            portRepository.save(port);
        }
    }

    @Override
    public Port getPortByPosition(List<Port> ports, int position) {
        if (ports == null || ports.isEmpty()) {
            throw new CustomException(
                    ErrorType.PORTS_LIST_IS_EMPTY
            );
        }
        // Verificar si la posición está dentro del rango permitido
        int minPosition = 1;
        int maxPosition = ports.size();

        if (position < minPosition || position > maxPosition) {
            String errorMsg = "Position must be between " + minPosition + " and " + maxPosition;
            throw new CustomException(
                    ErrorType.PORT_MUST_BE_IN_RANGE,
                    errorMsg
            );
        }
        String errorMsg = "Not found port with position: " + position;
        Port portChosen = ports.stream()
                .filter(port -> port.getPosition() == position)
                .findFirst()
                .orElseThrow(() -> new CustomException(
                        ErrorType.PORT_NOT_FOUND,
                        errorMsg
                ));

        if(portChosen.getActive()){
            throw new CustomException(
                    ErrorType.PORT_NOT_AVAILABLE
            );
        }
        return portChosen;
    }

    @Override
    public List<Port> getPortsAviable(List<Port> ports){
        return ports.stream()
                .filter(port -> !port.getActive())
                .collect(Collectors.toList());
    }

    @Override
    public List<Port> getPortsDisable(List<Port> ports){
        return ports.stream()
                .filter(port -> port.getActive())
                .collect(Collectors.toList());
    }

    @Override
    public Port getAvailablePort(List<Port> ports, Integer position) {
        if (ports == null || position == null) {
            throw new CustomException(
                    ErrorType.PORTS_LIST_OR_POSITION_CANNOT_BE_NULL
            );
        }

        return ports.stream()
                .filter(port -> port.getPosition().equals(position) && !port.getActive())
                .findFirst()
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.PORT_NOT_AVAILABLE,
                                "No available port found at position: " + position
                        )
                );
    }
    @Override
    public void freePort(Port port) {
        if (port != null && port.getActive()) {
            port.setActive(false);
            portRepository.save(port);
        }
    }

}
