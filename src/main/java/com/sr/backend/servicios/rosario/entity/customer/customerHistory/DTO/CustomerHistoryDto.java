package com.serviciosRosario.ServiciosRosario.entity.customer.customerHistory.DTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.serviciosRosario.ServiciosRosario.entity.customer.Customer;
import com.serviciosRosario.ServiciosRosario.enums.clientHistory.ClientModule;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerHistoryDto {
    private Integer id;

    private LocalDateTime date;

    private ClientModule module;

    private Customer customer;

    private String action;

    private String comment;

    private Integer operatorId;
}
