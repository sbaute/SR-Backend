package com.serviciosRosario.ServiciosRosario.entity.ticket.ticketcomment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TicketComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_public")
    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonBackReference("ticket-comment")
    private Ticket ticket;
}
