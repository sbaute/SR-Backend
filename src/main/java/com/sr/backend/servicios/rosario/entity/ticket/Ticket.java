package com.serviciosRosario.ServiciosRosario.entity.ticket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketarea.TicketArea;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcategory.TicketCategory;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment.TicketComment;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketpriority.TicketPriority;
import com.serviciosRosario.ServiciosRosario.entity.ticket.ticketstatus.TicketStatus;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference("customer-ticket")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_id", nullable = true)
    @JsonBackReference("user-ticket-assigner")
    private User assigned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", nullable = false)
    @JsonBackReference("user-ticket-operator")
    private User operator;

    @ManyToOne
    @JoinColumn(name = "priority", nullable = false)
    @JsonBackReference("ticket-ticketPriority")
    private TicketPriority priority;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    @JsonBackReference("ticket-ticketCategory")
    private TicketCategory category;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    @JsonBackReference("ticket-ticketStatus")
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "area", nullable = false)
    @JsonBackReference("ticket-ticketArea")
    private TicketArea area;

    @Column(name= "price")
    //@NotNull(message = "Price is mandatory")
    private Double price;

    @Column(name = "service_visit", nullable = true)
    private LocalDate serviceVisit;

    @Column(name = "activation_date")
    private LocalDate activationDate;

    @Column(name = "closed_date")
    private LocalDate closedDate;

    @Column(name = "last_modified", nullable = true)
    private LocalDate lastModified;

    @Column(name = "customer_created")
    private Boolean customerCreated = false;

    //@NotBlank(message = "Address can't be blank")
    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "between_address1")
    private String betweenAddress1;

    @Column(name = "between_address2")
    private String betweenAddress2;

    @ManyToOne
    @JoinColumn(name = "connection", nullable = true)
    @JsonBackReference("temporary-connection-ticket")
    private Connection connection;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonManagedReference("ticket-comment")
    private List<TicketComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonManagedReference("ticket-history")
    private List<History> histories = new ArrayList<>();
}
