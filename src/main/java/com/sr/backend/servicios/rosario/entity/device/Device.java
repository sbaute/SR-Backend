package com.serviciosRosario.ServiciosRosario.entity.device;

import com.serviciosRosario.ServiciosRosario.enums.device.ONUType;
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
public class Device {
    //Falta terminar este objeto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "deviceModel")
    private ONUType onuType;



}
