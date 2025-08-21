package com.serviciosRosario.ServiciosRosario.service.connection;

import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.box.port.Port;
import com.serviciosRosario.ServiciosRosario.entity.customer.customerHistory.CustomerHistory;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionResponseDto;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.enums.history.Action;

import java.util.List;

public interface IConnectionService {
    List<Connection> getAll();
    Connection getById(Integer id);
    Connection create(ConnectionRequestDto connectionRequestDto);
    Connection update(ConnectionRequestDto connectionRequestDto);
    void delete(Connection connection);
    boolean existsById(Integer id);
    ConnectionResponseDto convertToDto(Connection connection);
    void updatePlanIfNeeded(Connection connection, Integer planId, List<History> histories);
    void updateAddressIpIfNeeded(Connection connection, String newAddressIp, List<History> histories);
    History createClientHistory(Connection connection, Action action, String comment);
    void handleBoxChange(Connection connection, Box oldBox, Port oldPort, Integer newBoxId, Integer newPortPosition, List<History> histories);
    void handlePortChange(Connection connection, Box oldBox, Port oldPort, Integer newPortPosition, List<History> histories);
    Connection updateConnectionBox(Connection connection, Integer newBoxId, Integer newPortPosition, List<History> histories);
    void assignPortToConnection(Connection connection, Box box, Port port);
}
