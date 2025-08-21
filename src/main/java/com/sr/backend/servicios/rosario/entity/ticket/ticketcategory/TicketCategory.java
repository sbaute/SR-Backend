package com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcategory;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ticket_categories")
public class TicketCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "comment")
    private String comment;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "color")
    private String color;

    @Column(name = "colortext")
    private String colorText;

    @Column(name = "view_customer")
    private String viewCustomer;

    @Column(name = "cat_tickets")
    private Boolean catTickets;

    @Column(name = "cat_op")
    private Boolean cat_op;

    @Column(name = "discontinued")
    private Boolean discontinued;

    @Column(name = "translation")
    private String translation;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference("ticket-ticketCategory")
    private List<Ticket> tickets = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
