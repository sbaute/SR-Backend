package com.serviciosRosario.ServiciosRosario.entity.notifyBroker;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.enums.notifyBroker.NotifyType;
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
public class NotifyBroker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name= "title")
    private String title;

    @Column(name= "body")
    private String body;

    @Column(name= "sent")
    private Boolean sent;

    @Column(name= "comment")
    private String comment;

    @Column(name= "destiny")
    private String destiny;

    @Column(name= "type")
    private NotifyType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-notify")
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference("customer-notify")
    private Customer customer;

}
