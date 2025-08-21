package com.serviciosRosario.ServiciosRosario.entity.customer.customerHistory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.enums.clientHistory.ClientModule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client_history")
public class CustomerHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "module")
    private ClientModule module;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    @JsonBackReference("customer-clienthistory")
    private Customer customer;

    @Column(name = "action")
    private String action;

    @Lob
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "operatorId", nullable = true)
    @JsonBackReference("user-customer-history")
    private User operator;
}
