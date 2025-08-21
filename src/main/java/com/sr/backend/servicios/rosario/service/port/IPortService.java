package com.serviciosRosario.ServiciosRosario.service.port;

import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.box.port.Port;

import java.util.List;

public interface IPortService {

    List<Port> getAll();
    Port getById(Integer id);
    Port create(Port port);
    void delete(Port Port);
    boolean existsById(Integer id);
    Port update (Port port);
    void createPositionsOfPorts(Integer portQuantity, Box box);
    Port getPortByPosition(List<Port> ports, int position);
    List<Port> getPortsAviable(List<Port> ports);
    List<Port> getPortsDisable(List<Port> ports);
    Port getAvailablePort(List<Port> ports, Integer position);
    void freePort(Port port);
}
