package com.serviciosRosario.ServiciosRosario.service.connection.impl;

import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.enums.history.Action;
import com.serviciosRosario.ServiciosRosario.enums.history.ModuleHistory;
import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import com.serviciosRosario.ServiciosRosario.service.box.impl.BoxServiceImpl;
import com.serviciosRosario.ServiciosRosario.service.connection.IConnectionService;
import com.serviciosRosario.ServiciosRosario.service.port.impl.PortServiceImpl;

import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.box.DTO.BoxName;
import com.serviciosRosario.ServiciosRosario.entity.box.port.Port;
import com.serviciosRosario.ServiciosRosario.entity.customer.DTO.CustomerNameDto;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionResponseDto;
import com.serviciosRosario.ServiciosRosario.entity.plan.DTO.PlanNameDto;
import com.serviciosRosario.ServiciosRosario.entity.plan.Plan;
import com.serviciosRosario.ServiciosRosario.enums.clientHistory.ClientModule;
import com.serviciosRosario.ServiciosRosario.exceptions.CustomException;
import com.serviciosRosario.ServiciosRosario.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionServiceImpl implements IConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private PortServiceImpl portService;

    @Autowired
    private PortRepository portRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private BoxServiceImpl boxService;

    @Override
    public List<Connection> getAll() {
        return (List<Connection>) connectionRepository.findAll();
    }

    @Override
    public Connection getById(Integer id) {
        return connectionRepository.findById(id).orElse(null);
    }

    @Override
    public Connection create(ConnectionRequestDto connectionRequestDto) {

        Connection connection = new Connection();

        Customer customer = customerRepository.findById(connectionRequestDto.getClientId())
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.NOT_FOUND,
                                "Client not found with ID: " + connectionRequestDto.getClientId(),
                                HttpStatus.BAD_REQUEST.value()
                        ));
        connection.setCustomer(customer);

        Plan plan = planRepository.findById(connectionRequestDto.getPlanId())
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.NOT_FOUND,
                                "Plan not found with ID: " + connectionRequestDto.getPlanId(),
                                HttpStatus.BAD_REQUEST.value()
                        ));
        connection.setPlan(plan);

        Box box = boxRepository.findById(connectionRequestDto.getBoxId())
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.NOT_FOUND,
                                "Box not found with ID: " + connectionRequestDto.getBoxId(),
                                HttpStatus.BAD_REQUEST.value()
                        ));
        if(!box.getActive()){
            throw new CustomException(
                    ErrorType.BOX_CANNOT_BE_INACTIVE
            );
        }
        connection.setInitialDate(LocalDateTime.now());
        connection.setNodo("NODO PRINCIPAL");
        connection.setAddressIp(connectionRequestDto.getAddressIp());
        Port port = null;
        try{
            port = portService.getPortByPosition(box.getPorts(), connectionRequestDto.getPortPosition());
        } catch (CustomException ex) {
            throw new CustomException(
                    ErrorType.BAD_REQUEST,
                    ex.getMessage(),
                    ex.getStatus()
            );
        }
        port.setActive(true);
        port.setCustomer(customer);
        customer.getPorts().add(port);
        portService.update(port);
        connection.setPortId(port.getId());
        Integer newAvailablePort = box.getAviablePort() - 1;
        box.setAviablePort(newAvailablePort);
        connection.setBox(box);

        Connection connectionSaved =  connectionRepository.save(connection);

        History history = new History();
        history.setDate(LocalDateTime.now());
        history.setModule(ModuleHistory.CONNECTION);
        history.setCustomer(customer);
        history.setAction(Action.CONNECTION_CREATED);
        history.setTo("");
        history.setFrom("");
        String stringComment = "id: "+ connectionSaved.getId();
        history.setComment(stringComment);
        history.setUser(this.getOperatorByUsername());
        customer.getHistories().add(history);
        customerRepository.save(customer);

        return connectionSaved;
    }
    private User getOperatorByUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername((String) principal)
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));
        return user;
    }

    @Override
    public Connection update(ConnectionRequestDto connectionRequestDto) {
        Connection connection = null;
        List<History> histories = new ArrayList<>();

        connection = connectionRepository.findById(connectionRequestDto.getId())
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.CONNECTION_NOT_FOUND
                        ));

        updatePlanIfNeeded(connection, connectionRequestDto.getPlanId(), histories);

        // Actualizar la dirección IP si es necesario
        updateAddressIpIfNeeded(connection, connectionRequestDto.getAddressIp(), histories);

        Connection updatedConnection = updateConnectionBox(connection, connectionRequestDto.getBoxId(), connectionRequestDto.getPortPosition(), histories);

        historyRepository.saveAll(histories);

        return updatedConnection;
    }
    @Override
    public void updatePlanIfNeeded(Connection connection, Integer planId, List<History> histories) {
        if (planId != null) {
            Plan newPlan = planRepository.findById(planId)
                    .orElseThrow(() ->
                            new CustomException(
                                    ErrorType.NOT_FOUND,
                                    "Plan not found with ID: " + planId,
                                    HttpStatus.BAD_REQUEST.value()
                            ));

            if (!newPlan.getId().equals(connection.getPlan().getId())) {
                String comment = "(Plan cambiado) " + connection.getPlan().getName() + " > " + newPlan.getName();
                connection.setPlan(newPlan);
                histories.add(createClientHistory(connection, Action.CONNECTION_MODIFIED, comment));
            }
        }
    }
    @Override
    public void updateAddressIpIfNeeded(Connection connection, String newAddressIp, List<History> histories) {
        if (newAddressIp != null && !newAddressIp.equals(connection.getAddressIp())) {
            String comment = "(Ip modificada) " + connection.getAddressIp() + " > " + newAddressIp;
            histories.add(createClientHistory(connection, Action.CONNECTION_MODIFIED, comment));

            connection.setAddressIp(newAddressIp);
        }
    }
    public History createClientHistory(Connection connection, Action action, String comment) {
        History customerHistory = new History();
        customerHistory.setDate(LocalDateTime.now());
        customerHistory.setModule(ModuleHistory.CONNECTION);
        customerHistory.setCustomer(connection.getCustomer());
        customerHistory.setAction(action);
        customerHistory.setComment(comment);
        customerHistory.setUser(this.getOperatorByUsername());
        return customerHistory;
    }
    @Override
    public Connection updateConnectionBox(Connection connection, Integer newBoxId, Integer newPortPosition, List<History> histories) {
        Box oldBox = connection.getBox();
        Port oldPort =  portRepository.findById(connection.getPortId())
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.NOT_FOUND,
                                "Port not found with ID: " + connection.getPortId(),
                                HttpStatus.BAD_REQUEST.value()
                        ));

        if (newBoxId != null && !oldBox.getId().equals(newBoxId)) {
            handleBoxChange(connection, oldBox, oldPort, newBoxId, newPortPosition, histories);
        } else if (newPortPosition != null && !newPortPosition.equals(oldPort.getPosition())) {
            handlePortChange(connection, oldBox, oldPort, newPortPosition, histories);
        }

        portRepository.save(oldPort);
        return connectionRepository.save(connection);
    }
    @Override
    public void handleBoxChange(Connection connection, Box oldBox, Port oldPort, Integer newBoxId, Integer newPortPosition, List<History> histories) {
        portService.freePort(oldPort);

        Box newBox = boxRepository.findById(newBoxId)
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.NOT_FOUND,
                                "Box not found with ID: " + newBoxId,
                                HttpStatus.BAD_REQUEST.value()
                        ));
        if(!newBox.getActive()){
            throw new CustomException(
                    ErrorType.NEW_BOX_IS_INNACTIVE
            );
        }
        Port newPort = portService.getAvailablePort(newBox.getPorts(), newPortPosition);

        assignPortToConnection(connection, newBox, newPort);

        boxService.incrementAvailablePorts(oldBox);

        String comment = "(CAJA): " + oldBox.getId() + " > " + newBox.getId() +
                " \n (PORT): " + oldPort.getPosition() + " > " + newPortPosition;
        histories.add(createClientHistory(connection, Action.CONNECTION_MODIFIED, comment));
    }
    @Override
    public void handlePortChange(Connection connection, Box oldBox, Port oldPort, Integer newPortPosition, List<History> histories) {
        portService.freePort(oldPort);

        Port newPort = portService.getAvailablePort(oldBox.getPorts(), newPortPosition);
        assignPortToConnection(connection, oldBox, newPort);

        String comment = "(PORT): " + oldPort.getPosition() + " > " + newPortPosition;
        histories.add(createClientHistory(connection, Action.CONNECTION_MODIFIED, comment));
    }
    @Override
    public void assignPortToConnection(Connection connection, Box box, Port port) {
        if (box == null || port == null) {
                    throw new CustomException(
                            ErrorType.BOX_AND_PORT_CANNOT_BE_NULL
                    );
        }

        connection.setBox(box);
        connection.setPortId(port.getId());
        port.setActive(true);
        portRepository.save(port);

        // Actualizar los puertos disponibles en la caja
        box.setAviablePort(box.getAviablePort() - 1);
        boxRepository.save(box);
    }
    @Override
    public void delete(Connection connectionRequest) {
        Connection connection = connectionRepository.findById(connectionRequest.getId())
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.CONNECTION_NOT_FOUND
                        ));


        Port port = portRepository.findById(connection.getPortId()).orElseThrow(
                () -> new CustomException(
                        ErrorType.CONNECTION_NO_PORT_ASSIGNED
                )
        );
        port.setActive(Boolean.FALSE);
        portRepository.save(port);

        connectionRepository.delete(connection);
    }

    @Override
    public boolean existsById(Integer id) {
        return connectionRepository.existsById(id);
    }

    @Override
    public ConnectionResponseDto convertToDto(Connection connection) {
        return ConnectionResponseDto.builder()
                .id(connection.getId())
                .client(CustomerNameDto.builder()
                        .id(connection.getCustomer().getId())
                        .name(connection.getCustomer().getName())
                        .build())
                .planName(PlanNameDto.builder().name(connection.getPlan().getName()).build())
                .boxName(BoxName.builder().name(connection.getBox().getBoxNumber()).build())
                .addressIp(connection.getAddressIp())
                .build();
    }
}
