package com.serviciosRosario.ServiciosRosario.entity.history;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.enums.history.Action;
import com.serviciosRosario.ServiciosRosario.enums.history.ModuleHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private Action action;

    @Enumerated(EnumType.STRING)
    @Column(name = "module")
    private ModuleHistory module;

    //Agregar el objeto cuando esten los Usuarios creados
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @JsonBackReference("user-history")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = true)
    @JsonBackReference("ticket-history")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference("customer-history")
    private Customer customer;

    //Estos son datos que dicen de que estado a que estado pasa
    @Column(name = "from_state")
    private String from;

    @Column(name = "to_state")
    private String to;

    @Column(name = "comment")
    private String comment;
}
