package com.serviciosRosario.ServiciosRosario.entity.connection;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.plan.Plan;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.enums.connection.ConnectionZone;
import com.serviciosRosario.ServiciosRosario.enums.connection.RouterAccess;
import com.serviciosRosario.ServiciosRosario.enums.connection.TypeConnectionRadius;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Connection {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @Column(name = "initial_date")
    private LocalDateTime initialDate;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(name = "nodo")
    private String nodo;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_connection_radius")
    private TypeConnectionRadius typeConnectionRadius;

    @Column(name = "user_connection")
    private String userConnection;

    @Column(name= "password")
    private String password;

    @Column(name = "address_ip")
    private String addressIp;

    @Enumerated(EnumType.STRING)
    @Column(name = "router_access")
    private RouterAccess routerAccess;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_point")
    private RouterAccess AccessPoint;

    @ManyToOne
    @JoinColumn(name = "box_id")
    private Box box;

    @Column(name = "port_id")
    private Integer portId;

    @OneToMany(mappedBy = "connection", cascade = CascadeType.ALL)
    @JsonManagedReference("temporary-connection-ticket")
    private List<Ticket> tickets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "zone")
    private ConnectionZone zone;
}
