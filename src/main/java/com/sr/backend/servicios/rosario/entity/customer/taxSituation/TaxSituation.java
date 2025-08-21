package com.serviciosRosario.ServiciosRosario.entity.customer.taxSituation;

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
public class TaxSituation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "by_default")
    private Boolean byDefault;

    @Column(name = "translation")
    private String translation;
}
