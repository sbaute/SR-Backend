package com.serviciosRosario.ServiciosRosario.service.customer.impl;

import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTORes;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.enums.history.Action;
import com.serviciosRosario.ServiciosRosario.enums.history.ModuleHistory;
import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import com.serviciosRosario.ServiciosRosario.repository.UserRepository;
import com.serviciosRosario.ServiciosRosario.service.customer.ICustomerService;
import com.serviciosRosario.ServiciosRosario.service.ticket.impl.TicketServiceImpl;
import com.serviciosRosario.ServiciosRosario.entity.customer.DTO.CustomerDto;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import com.serviciosRosario.ServiciosRosario.entity.connection.DTO.ConnectionRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.enums.customers.StatusCustomer;
import com.serviciosRosario.ServiciosRosario.exceptions.CustomException;
import com.serviciosRosario.ServiciosRosario.repository.CustomerRepository;
import com.serviciosRosario.ServiciosRosario.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private com.serviciosRosario.ServiciosRosario.service.connection.impl.ConnectionServiceImpl connectionService;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketServiceImpl ticketService;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAllWithEmails();
    }

    @Override
    @Transactional
    public Customer getById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                ErrorType.CUSTOMER_NOT_FOUND
        ));
    }

    @Override
    @Transactional
    public Customer create(CustomerDto customerDto) {

        Customer customer = new Customer();

        customer.setName(customerDto.getName());
        customer.setCode(this.generateCustomerCode());

        customer.setTaxResidence(customerDto.getTaxResidence());

        customer.setDocNumber(customerDto.getDocNumber());
        customer.setEmails(customerDto.getEmail());
        customer.setAddresses(customerDto.getAddress());
        customer.setBetweenAddress1(customerDto.getBetweenAddress1());
        customer.setBetweenAddress2(customerDto.getBetweenAddress2());
        customer.setStatus(StatusCustomer.enabled);
        customer.setTemporary(Boolean.FALSE);

        History history = new History();
        history.setDate(LocalDateTime.now());
        history.setModule(ModuleHistory.CUSTOMER);
        history.setCustomer(customer);
        history.setAction(Action.CUSTOMER_CREATED);
        history.setTo("");
        history.setFrom("");
        history.setComment("-");
        history.setUser(this.getOperatorByUsername());

        customer.getHistories().add(history);

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer createTemporary(CustomerDto customerDto){
        Customer customer = new Customer();

        customer.setName(customerDto.getName());
        customer.setCode(this.generateTemporaryCode());

        customer.setTaxResidence(customerDto.getTaxResidence());

        customer.setDocNumber(customerDto.getDocNumber());
        customer.setEmails(customerDto.getEmail());
        customer.setAddresses(customerDto.getAddress());
        customer.setBetweenAddress1(customerDto.getBetweenAddress1());
        customer.setBetweenAddress2(customerDto.getBetweenAddress2());
        customer.setStatus(StatusCustomer.no_service);
        customer.setTemporary(Boolean.TRUE);

        History history = new History();
        history.setDate(LocalDateTime.now());
        history.setModule(ModuleHistory.CUSTOMER);
        history.setCustomer(customer);
        history.setAction(Action.TEMPORARY_CUSTOMER_CREATED);
        history.setTo("");
        history.setFrom("");
        history.setComment("-");
        history.setUser(this.getOperatorByUsername());

        customer.getHistories().add(history);

        if(customer.getTickets() == null){
            throw new CustomException(
                    ErrorType.TEMPORARY_NOT_TICKET
            );
        }
        TicketDTO ticketDTO = customerDto.getTickets().getFirst();

        Customer customerSaved = customerRepository.save(customer);

        Ticket ticketSaved = ticketService.create(ticketDTO, customerSaved.getId());

        //customer.getTickets().add(ticketSaved);

        return customerSaved;
    }

    private User getOperatorByUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername((String) principal)
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));
        return user;
    }

    @Override
    @Transactional
    public Customer update(CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerDto.getId())
                .orElseThrow(() -> new CustomException(
                        ErrorType.CUSTOMER_NOT_FOUND
                ));

        List<History> histories = new ArrayList<>();

        User operator = this.getOperatorByUsername();

        updateFieldIfChanged(
                customerDto.getName(),
                customer.getName(),
                "Name",
                customer::setName,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getTaxResidence(),
                customer.getTaxResidence(),
                "TaxResidence",
                customer::setTaxResidence,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getCity(),
                customer.getCity(),
                "City",
                customer::setCity,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        if (customerDto.getType() != null && !Objects.equals(customer.getType(), customerDto.getType())) {
            histories.add(createHistory(
                    customer,
                    ModuleHistory.CUSTOMER,
                    Action.CUSTOMER_MODIFIED,
                    "Type",
                    customer.getType() != null ? customer.getType().toString() : "",
                    customerDto.getType().toString()
            ));
            customer.setType(customerDto.getType());
        }
        if (customerDto.getTaxSituation() != null && !Objects.equals(customer.getTaxSituation(), customerDto.getTaxSituation())) {
            histories.add(createHistory(
                    customer,
                    ModuleHistory.CUSTOMER,
                    Action.CUSTOMER_MODIFIED,
                    "TaxSituation",
                    customer.getTaxSituation() != null ? customer.getTaxSituation().toString() : "",
                    customerDto.getTaxSituation().toString()
            ));
            customer.setTaxSituation(customerDto.getTaxSituation());
        }
        if (customerDto.getIdentificationType() != null && !Objects.equals(customer.getIdentificationType(), customerDto.getIdentificationType())) {
            histories.add(createHistory(
                    customer,
                    ModuleHistory.CUSTOMER,
                    Action.CUSTOMER_MODIFIED,
                    "IdentificationType",
                    customer.getIdentificationType() != null ? customer.getIdentificationType().toString() : "",
                    customerDto.getIdentificationType().toString()
            ));
            customer.setIdentificationType(customerDto.getIdentificationType());
        }
        updateFieldIfChanged(
                customerDto.getNickname(),
                customer.getNickname(),
                "Nickname",
                customer::setNickname,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getComercialActivity(),
                customer.getComercialActivity(),
                "ComercialActivity",
                customer::setComercialActivity,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );


        updateFieldIfChanged(
                customerDto.getDocNumber(),
                customer.getDocNumber(),
                "DocNumber",
                customer::setDocNumber,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        if (customerDto.getPhones() != null && !Objects.equals(customer.getPhones(), customerDto.getPhones())) {
            histories.add(createHistory(
                    customer,
                    ModuleHistory.CUSTOMER,
                    Action.CUSTOMER_MODIFIED,
                    "Phones",
                    customer.getPhones() != null ? customer.getPhones().toString() : "null",
                    customerDto.getPhones().toString()
            ));
            customer.setPhones(new ArrayList<>(customerDto.getPhones()));
        }

        if (customerDto.getEmail() != null && !Objects.equals(customer.getEmails(), customerDto.getEmail())) {
            histories.add(createHistory(
                    customer,
                    ModuleHistory.CUSTOMER,
                    Action.CUSTOMER_MODIFIED,
                    "Emails",
                    customer.getEmails() != null ? customer.getEmails().toString() : "null",
                    customerDto.getEmail().toString()
            ));
            customer.setEmails(new ArrayList<>(customerDto.getEmail()));
        }
        if (customerDto.getAddress() != null && !Objects.equals(customer.getAddresses(), customerDto.getAddress())) {
            histories.add(createHistory(
                    customer,
                    ModuleHistory.CUSTOMER,
                    Action.CUSTOMER_MODIFIED,
                    "Address",
                    customer.getAddresses() != null ? customer.getAddresses().toString() : "null",
                    customerDto.getAddress().toString()
            ));
            customer.setAddresses(new ArrayList<>(customerDto.getAddress()));
        }

        updateFieldIfChanged(
                customerDto.getBetweenAddress1(),
                customer.getBetweenAddress1(),
                "BetweenAddress1",
                customer::setBetweenAddress1,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getBetweenAddress2(),
                customer.getBetweenAddress2(),
                "BetweenAddress2",
                customer::setBetweenAddress2,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getLat(),
                customer.getLat(),
                "Lat",
                customer::setLat,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getLng(),
                customer.getLng(),
                "Lng",
                customer::setLng,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getPortalPassword(),
                customer.getPortalPassword(),
                "Portal Password",
                customer::setPortalPassword,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getExtra1(),
                customer.getExtra1(),
                "Extra 1",
                customer::setExtra1,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );
        updateFieldIfChanged(
                customerDto.getExtra2(),
                customer.getExtra2(),
                "Extra 2",
                customer::setExtra2,
                customer,
                ModuleHistory.CUSTOMER,
                Action.CUSTOMER_MODIFIED,
                histories
        );


        customer.getHistories().addAll(histories);

        return customerRepository.save(customer);
    }


    @Override
    @Transactional
    public void delete(Customer customer) {
        customer.setStatus(StatusCustomer.blocked);
        customerRepository.save(customer);
    }
    @Override
    public String generateCustomerCode(){
        Integer maxCode = customerRepository.findMaxPermanentCustomerCode();
        System.out.println("Variable: " + maxCode);
        int nextCode = (maxCode != null) ? maxCode + 1 : 1;
        return String.valueOf(nextCode);
    }
    @Override
    public String generateTemporaryCode() {
        String prefix = "YY";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);
        return prefix + timestamp;
    }
    @Override
    public LocalDateTime decodeTemporaryToDate(String code) {
        // Elimina el prefijo "YY"
        String timestamp = code.substring(2); // Salta las dos primeras letras
        // Define el formato correspondiente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        // Convierte el string en un LocalDateTime
        return LocalDateTime.parse(timestamp, formatter);
    }

    @Override
    public boolean existsById(Integer id) {
        return customerRepository.existsById(id);
    }

    @Override
    public TicketDTORes assignTicket(Integer customerId, TicketDTO ticketDTO){
        Customer customer = null;

        if(customerId != null && customerRepository.existsById(customerId)){
            customer = customerRepository.findById(customerId)
                    .orElseThrow(() ->
                            new CustomException(
                                    ErrorType.CUSTOMER_NOT_FOUND_WITH_ID
                            ));
        }
        Ticket ticketSaved = ticketService.create(ticketDTO, customerId);

        return ticketService.convertToDtoRes(ticketSaved);
    }
    @Override
    public CustomerDto convertToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .tickets(customer.getTickets().stream()
                        .map(ticketService::convertToDto) // Método para convertir Ticket a TicketDTO
                        .collect(Collectors.toList()))
                .docNumber(customer.getDocNumber())
                .email(customer.getEmails())
                .address(customer.getAddresses())
                .betweenAddress1(customer.getBetweenAddress1())
                .betweenAddress2(customer.getBetweenAddress2())
                .histories(customer.getHistories())
                .ports(customer.getPorts())
                .build();
    }
    public History createHistory(Customer customer, ModuleHistory module, Action action,
                                  String field, String from, String to) {
        History history = new History();
        history.setDate(LocalDateTime.now());
        history.setModule(module);
        history.setCustomer(customer);
        history.setAction(action);
        history.setFrom(from != null ? from : "");
        history.setTo(to != null ? to : "");
        history.setComment(field + " modified");
        history.setUser(this.getOperatorByUsername());
        return history;
    }
    private void updateFieldIfChanged(
            String newValue, String oldValue,
            String fieldName, Consumer<String> updater,
            Customer customer, ModuleHistory module, Action action,
            List<History> histories) {

        if (newValue != null && !Objects.equals(newValue, oldValue)) {
            histories.add(createHistory(
                    customer,
                    module,
                    action,
                    fieldName,
                    oldValue,
                    newValue
            ));
            updater.accept(newValue); // Actualiza el campo
        }
    }

    @Override
    public Connection createConnection(Integer clientId, ConnectionRequestDto connectionRequestDto) {

        ConnectionRequestDto newConnection = ConnectionRequestDto.builder()
                .id(connectionRequestDto.getId())
                .clientId(clientId)
                .planId(connectionRequestDto.getPlanId())
                .boxId(connectionRequestDto.getBoxId())
                .addressIp(connectionRequestDto.getAddressIp())
                .portPosition(connectionRequestDto.getPortPosition())
                .build();

        return connectionService.create(newConnection);
    }
}
