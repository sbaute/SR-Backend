package com.serviciosRosario.ServiciosRosario.entity.ticket.ticketstatus;

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
@Table(name = "ticket_statuses")
public class TicketStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    @JsonManagedReference("ticket-ticketStatus")
    private List<Ticket> tickets = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
