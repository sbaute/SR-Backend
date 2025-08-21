package com.serviciosRosario.ServiciosRosario.entity.customer.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.serviciosRosario.ServiciosRosario.entity.box.port.Port;
import com.serviciosRosario.ServiciosRosario.entity.customer.customerHistory.CustomerHistory;
import com.serviciosRosario.ServiciosRosario.entity.customer.identificationType.IdentificationType;
import com.serviciosRosario.ServiciosRosario.entity.customer.taxSituation.TaxSituation;
import com.serviciosRosario.ServiciosRosario.entity.history.History;
import com.serviciosRosario.ServiciosRosario.entity.ticket.DTO.TicketDTO;
import com.serviciosRosario.ServiciosRosario.entity.ticket.Ticket;
import com.serviciosRosario.ServiciosRosario.enums.customers.TypeCustomer;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private Integer id;
    private String code;
    private String name;
    private String taxResidence;
    private String city;
    private TypeCustomer type;
    private TaxSituation taxSituation;
    private IdentificationType identificationType;
    private String docNumber;
    private String nickname;
    private String comercialActivity;
    private List<String> phones;
    private List<String> email;
    private List<String> address;
    private String betweenAddress1;
    private String betweenAddress2;
    private String lat;
    private String lng;
    private String portalPassword;
    private String extra1;
    private String extra2;
    private List<TicketDTO> tickets;
    private List<History> histories;
    private List<Port> ports;
}
