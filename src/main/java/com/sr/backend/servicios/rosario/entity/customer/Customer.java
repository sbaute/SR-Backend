package com.serviciosRosario.ServiciosRosario.entity.customer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.serviciosRosario.ServiciosRosario.entity.box.port.Port;
import com.serviciosRosario.ServiciosRosario.entity.customer.identificationType.IdentificationType;
import com.serviciosRosario.ServiciosRosario.entity.customer.taxSituation.TaxSituation;
import com.serviciosRosario.ServiciosRosario.entity.connection.Connection;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.notifyBroker.NotifyBroker;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.enums.customers.StatusCustomer;
import com.serviciosRosario.ServiciosRosario.enums.customers.TypeCustomer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    //Esto será lo que utilicen como id finalmente
    @Column(name="code", nullable = false, unique = true)
    private String code;
    //

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "tax_residence", nullable = false)
    private String taxResidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeCustomer type;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "tax_situation")
    @JsonBackReference("customer_taxsituation")
    private TaxSituation taxSituation;

    @ManyToOne
    @JoinColumn(name = "identification_type")
    @JsonBackReference("customer_identificationtype")
    private IdentificationType identificationType;

    @Column(name = "doc_number", nullable = false, unique = true)
    private String docNumber;

    //Estos atributos no lo usan o lo usan mal
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "comercial_activity")
    private String comercialActivity;
    //

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_phones", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "phone")
    private List<String> phones;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_emails", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "email")
    private List<String> emails;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_addresses", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "address")
    private List<String> addresses;

    @Column(name = "between_address1" )
    private String betweenAddress1;

    @Column(name = "between_address2" )
    private String betweenAddress2;

    //Hay que poner una fk cuando exista la entidad
//    @Column(name = "collector_id")
//    private Integer collector;
//    @Column(name = "seller_id")
//    private Integer seller;
//    @Column(name = "business_id")
//    private Integer businessId;
    //
    @Column(name = "lat")
    private String lat;
    @Column(name = "lng")
    private String lng;
    @Column(name = "portal_password")
    private String portalPassword;
    @Column(name = "extra1")
    private String extra1;
    //al extra 2 lo están usando para almacenar los alias
    @Column(name = "extra2")
    private String extra2;
    //
//    @Column(name = "block")
//    private Boolean block;
//    @Column(name = "free")
//    private Boolean free;

//    @Column(name = "debt")
//    private Double debt;
//    @Column(name = "duedebt")
//    private Double duedebt;
//    @Column(name = "real_duedebt")
//    private Double realDuedebt;
//    @Column(name = "archived_date")
//    private Double archivedDate;
//    @Column(name = "duedebt_date")
//    private LocalDateTime duedebtDate;
//    @Column(name = "speed_limited")
//    private Boolean speedLimited;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusCustomer status;

//    @Column(name = "enable_date")
//    private LocalDateTime enableDate;
//
//    @Column(name = "block_date")
//    private LocalDateTime blockDate;

    @Column(name = "temporary")
    private Boolean temporary;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Connection> connections = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("customer-ticket")
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("customer-port")
    private List<Port> ports = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference("customer-history")
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference("customer-notify")
    private List<NotifyBroker> notifies = new ArrayList<>();

}
