package com.serviciosRosario.ServiciosRosario.entity.user;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.serviciosRosario.ServiciosRosario.entity.customer.customerHistory.CustomerHistory;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.notifyBroker.NotifyBroker;
import com.serviciosRosario.ServiciosRosario.entity.role.Role;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    // Relación para los tickets asignados por este usuario
    @OneToMany(mappedBy = "assigned", orphanRemoval = true)
    @JsonManagedReference("user-ticket-assigner")
    private List<Ticket> ticketsAssigned;

    // Relación para los tickets en los que este usuario es operador
    @OneToMany(mappedBy = "operator", orphanRemoval = true)
    @JsonManagedReference("user-ticket-operator")
    private List<Ticket> ticketsOperated;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-history")
    private List<History> histories;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-notify")
    private List<NotifyBroker> notifySent;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Agregar el rol como autoridad
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        // Agregar permisos asociados al rol
        if (role != null && role.getPermissions() != null) {
            authorities.addAll(role.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                    .collect(Collectors.toList()));
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
