package com.serviciosRosario.ServiciosRosario.entity.box.port;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.serviciosRosario.ServiciosRosario.entity.box.Box;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name = "position")
    private Integer position;

    @Column(name = "isActive")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    @JsonBackReference("box-port")
    private Box box;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    @JsonBackReference("customer-port")
    private Customer customer;
}
