package com.serviciosRosario.ServiciosRosario.entity.box;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.serviciosRosario.ServiciosRosario.entity.box.port.Port;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name= "box_number", unique = true)
    private String boxNumber;

    @Column(name = "port_quantity")
    private Integer portQuantity;

    @Column(name = "comments")
    private String comments;

    @Column(name = "power" )
    private Double power;

    @Column(name = "address")
    private String address;

    @Column(name = "aviable_port")
    private Integer aviablePort;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("box-port")
    private List<Port> ports;

}
