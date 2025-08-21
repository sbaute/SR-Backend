package com.serviciosRosario.ServiciosRosario.entity.ticket.DTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketarea.TicketArea;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcategory.TicketCategory;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.TicketComment;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketpriority.TicketPriority;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketstatus.TicketStatus;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDTO {
    private Integer id;

    private Integer customerId;

    private Integer assignedId;

    private Integer operatorId;

    private Integer areaId;

    private Integer categoryId;

    private Integer priorityId;

    private Integer statusId;

    private Double price;

    private LocalDate serviceVisit;

    private LocalDate activationDate;

    private LocalDate lastModified;

    private String address;

    private String lat;

    private String lng;

    private String betweenAddress1;

    private String betweenAddress2;

    private List<TicketComment> comments;

    private List<History> histories;

    private Connection connection;
}
