package com.serviciosRosario.ServiciosRosario.service.ticket.impl;

import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTORes;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketarea.TicketArea;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcategory.TicketCategory;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketpriority.TicketPriority;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketstatus.TicketStatus;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.enums.history.ModuleHistory;
import com.serviciosRosario.ServiciosRosario.enums.ticket.Area;
import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import com.serviciosRosario.ServiciosRosario.repository.*;
import com.serviciosRosario.ServiciosRosario.service.ticket.ITicketService;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.enums.history.Action;
import com.serviciosRosario.ServiciosRosario.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class TicketServiceImpl implements ITicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerHistoryRepository customerHistoryRepository;
    @Autowired
    private TicketAreaRepository ticketAreaRepository;
    @Autowired
    private TicketStatusRepository ticketStatusRepository;
    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;


    private User getOperatorByUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername((String) principal)
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));
    }

    @Override
    public List<Ticket> getAll() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    @Override
    public Ticket getById(Integer id){
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket create(TicketDTO ticketDTO, Integer customerId){
        Ticket ticket = new Ticket();
        Customer customer = null;
        if(customerId != null){
            customer = findEntityById(
                    customerId,
                    customerRepository,
                    ErrorType.TICKET_NOT_CLIENT,
                    ErrorType.CUSTOMER_NOT_FOUND
            );
            ticket.setCustomer(customer);
        }else{
            if(ticketDTO.getCustomerId() == null){
                throw new CustomException(
                        ErrorType.TICKET_NOT_CLIENT
                );
            }
            customer = findEntityById(
                    ticketDTO.getCustomerId(),
                    customerRepository,
                    ErrorType.TICKET_NOT_CLIENT,
                    ErrorType.CUSTOMER_NOT_FOUND
            );
            ticket.setCustomer(customer);
        }

        //nullable = true
        User userAssigned = findEntityById(
                ticketDTO.getAssignedId(),
                userRepository,
                ErrorType.CUSTOMER_NOT_FOUND,
                ErrorType.CUSTOMER_NOT_FOUND
        );
        ticket.setAssigned(userAssigned);


        TicketArea area = findEntityById(
                ticketDTO.getAreaId(),
                ticketAreaRepository,
                ErrorType.TICKET_NOT_AREA,
                ErrorType.AREA_NOT_FOUND
        );
        ticket.setArea(area);

        TicketCategory category = findEntityById(
                ticketDTO.getCategoryId(),
                ticketCategoryRepository,
                ErrorType.TICKET_NOT_CATEGORY,
                ErrorType.CATEGORY_NOT_FOUND
        );
        ticket.setCategory(category);

        TicketPriority priority = findEntityById(
                ticketDTO.getPriorityId(),
                ticketPriorityRepository,
                ErrorType.TICKET_NOT_PRIORITY,
                ErrorType.PRIORITY_NOT_FOUND
        );
        ticket.setPriority(priority);

        TicketStatus status = findEntityById(
                ticketDTO.getStatusId(),
                ticketStatusRepository,
                ErrorType.TICKET_NOT_STATUS,
                ErrorType.STATUS_NOT_FOUND
        );
        ticket.setStatus(status);

        ticket.setAddress(ticketDTO.getAddress());
        ticket.setLat(ticketDTO.getLat());
        ticket.setLng(ticketDTO.getLng());
        ticket.setBetweenAddress1(ticketDTO.getBetweenAddress1());
        ticket.setBetweenAddress2(ticketDTO.getBetweenAddress2());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setServiceVisit(ticketDTO.getServiceVisit());
        ticket.setActivationDate(LocalDate.now());
        ticket.setComments(ticketDTO.getComments());
        ticket.setHistories(ticketDTO.getHistories());
        ticket.setConnection(ticketDTO.getConnection());
        ticket.setOperator(this.getOperatorByUsername());

        if (ticket.getHistories() == null) {
            ticket.setHistories(new ArrayList<>());
        }

        History history = new History();
        history.setDate(LocalDateTime.now());
        history.setFrom("-");
        history.setTo("-");
        history.setUser(this.getOperatorByUsername());
        history.setCustomer(customer);
        history.setAction(Action.TICKET_CREATED);

        history.setTicket(ticket);
        ticket.getHistories().add(history);

        return ticketRepository.save(ticket);
    }
    //Esta función agiliza las validaciones
    private <T> T findEntityById(Integer id, CrudRepository<T, Integer> repository, ErrorType errorTypeNull, ErrorType errorTypeNotFound) {
        if (id == null) {
            throw new CustomException(errorTypeNull);
        }
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(errorTypeNotFound));
    }

    @Override
    public Ticket update(TicketDTO ticketDTO){
        Ticket ticket = null;

        if (ticketDTO.getId() != null && ticketRepository.existsById(ticketDTO.getId())) {
            ticket = ticketRepository.findById(ticketDTO.getId())
                    .orElseThrow(() -> new CustomException(
                            ErrorType.TICKET_NOT_FOUND_WHIT_ID
                    ));
        }

        List<History> histories = new ArrayList<>();

        updateFieldByIdIfChanged(
                ticketDTO.getCustomerId(),
                customerRepository::findById,
                ticket.getCustomer(),
                "Customer",
                ticket::setCustomer,
                ticket,
                histories,
                ErrorType.TICKET_NOT_CLIENT
        );

        updateFieldByIdIfChanged(
                ticketDTO.getStatusId(),
                ticketStatusRepository::findById,
                ticket.getStatus(),
                "Status",
                ticket::setStatus,
                ticket,
                histories,
                ErrorType.STATUS_NOT_FOUND
        );

        updateFieldByIdIfChanged(
                ticketDTO.getAreaId(),
                ticketAreaRepository::findById,
                ticket.getArea(),
                "Area",
                ticket::setArea,
                ticket,
                histories,
                ErrorType.TICKET_NOT_AREA
        );

        updateFieldByIdIfChanged(
                ticketDTO.getPriorityId(),
                ticketPriorityRepository::findById,
                ticket.getPriority(),
                "Priority",
                ticket::setPriority,
                ticket,
                histories,
                ErrorType.PRIORITY_NOT_FOUND
        );
        updateFieldByIdIfChanged(
                ticketDTO.getCategoryId(),
                ticketCategoryRepository::findById,
                ticket.getCategory(),
                "Category",
                ticket::setCategory,
                ticket,
                histories,
                ErrorType.CATEGORY_NOT_FOUND
        );

        ticket.getHistories().addAll(histories);

        ticket.setLastModified(LocalDate.now());

        return ticketRepository.save(ticket);
    }

    @Override
    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }
    @Override
    public boolean existsById(Integer id) {
        return ticketRepository.existsById(id);
    }

    @Override
    public Customer convertTemporaryClientToPermanent(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new CustomException(
                                ErrorType.TICKET_NOT_FOUND
                        ));

        Customer customer = ticket.getCustomer();

        if(customer.getTemporary() == Boolean.FALSE){
            throw new CustomException(
                    ErrorType.CUSTOMER_ALREADY_PERMANENT
            );
        }

        customer.setTemporary(Boolean.FALSE);
        customer.setTickets(new ArrayList<Ticket>());
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }
    private <T, ID> void updateFieldByIdIfChanged(
            ID newId,
            Function<ID, Optional<T>> repositoryFetcher,
            T oldValue,
            String fieldName,
            Consumer<T> updater,
            Ticket ticket,
            List<History> histories,
            ErrorType errorType) {

        if (newId != null) {
            T newValue = repositoryFetcher.apply(newId).orElseThrow(() ->
                    new CustomException(errorType));
            if (!Objects.equals(newValue, oldValue)) {
                histories.add(createHistory(fieldName, oldValue, newValue, ticket));
                updater.accept(newValue);
            }
        }
    }

    private <T> History createHistory(
            String fieldName,
            T oldValue,
            T newValue,
            Ticket ticket) {

        History history = new History();
        history.setDate(LocalDateTime.now());
        history.setModule(ModuleHistory.TICKET);
        history.setAction(Action.TICKET_MODIFIED);
        history.setFrom(oldValue != null ? oldValue.toString() : "");
        history.setTo(newValue != null ? newValue.toString() : "");
        history.setComment(fieldName);
        history.setTicket(ticket);
        history.setCustomer(ticket.getCustomer());
        history.setUser(this.getOperatorByUsername());
        return history;
    }

    @Override
    public TicketDTO convertToDto(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .customerId(ticket.getCustomer().getId())
                .assignedId(ticket.getAssigned().getId())
                .operatorId(ticket.getOperator().getId())
                .areaId(ticket.getArea().getId())
                .categoryId(ticket.getCategory().getId())
                .priorityId(ticket.getPriority().getId())
                .statusId(ticket.getStatus().getId())
                .price(ticket.getPrice())
                .address(ticket.getAddress())
                .lat(ticket.getLat())
                .lng(ticket.getLng())
                .betweenAddress1(ticket.getBetweenAddress1())
                .betweenAddress2(ticket.getBetweenAddress2())
                .serviceVisit(ticket.getServiceVisit())
                .activationDate(ticket.getActivationDate())
                .lastModified(ticket.getLastModified())
                .comments(ticket.getComments())
                .histories(ticket.getHistories())
                .connection(ticket.getConnection())
                .build();
    }

    @Override
    public TicketDTORes convertToDtoRes(Ticket ticket) {
        return TicketDTORes.builder()
                .id(ticket.getId())
                .customerCode(ticket.getCustomer().getCode())
                .customerName(ticket.getCustomer().getName())
                .assignerName(ticket.getAssigned().getName())
                .operatorName(ticket.getOperator().getName())
                .areaName(ticket.getArea().getName())
                .categoryName(ticket.getCategory().getName())
                .priorityName(ticket.getPriority().getName())
                .statusName(ticket.getStatus().getName())
                .price(ticket.getPrice())
                .address(ticket.getAddress())
                .lat(ticket.getLat())
                .lng(ticket.getLng())
                .betweenAddress1(ticket.getBetweenAddress1())
                .betweenAddress2(ticket.getBetweenAddress2())
                .serviceVisit(ticket.getServiceVisit())
                .activationDate(ticket.getActivationDate())
                .lastModified(ticket.getLastModified())
                .commentsQuantity(ticket.getComments() != null ? ticket.getComments().size() : 0)
                .historiesQuantity(ticket.getHistories() != null ? ticket.getHistories().size() : 0)
                .connection(ticket.getConnection())
                .build();
    }
}
