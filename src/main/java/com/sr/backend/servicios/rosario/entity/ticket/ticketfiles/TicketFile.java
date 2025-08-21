package com.serviciosRosario.ServiciosRosario.entity.ticket.ticketfiles;

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
@Table(name = "ticket_files")
public class TicketFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name = "file")
    private String file;

    @Column(name = "internal")
    private Boolean internal;


}
