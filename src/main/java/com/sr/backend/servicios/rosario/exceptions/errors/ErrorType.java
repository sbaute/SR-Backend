package com.serviciosRosario.ServiciosRosario.exceptions.errors;

public enum ErrorType {

    //--Generic
    NOT_FOUND("El recurso solicitado no existe", 404),
    BAD_REQUEST("La solicitud es inválida", 400),
    NOT_RELATED("Los recursos no están relacionados", 422),
    IS_EMPTY("El campo está vacío", 400),

    //--User/Auth/Role/Permission
    USER_ALREADY_EXISTS("User already exists", 400),
    USER_NOT_FOUND("User not found", 404),
    USER_AUTHENTICATED_NOT_FOUND("User authenticated not found", 404),
    USER_PERMISSION_DENIED("You do not have permission", 403),
    USER_PERMISSION_DENIED_NOT_SUPERADMIN("You are not SuperAdmin, you do not have permission", 403),
    ROLE_NOT_FOUND("Role not found", 404),
    //--Password
    PASSWORDS_EMPTY("Passwords cannot be empty", 400),
    PASSWORDS_DO_NOT_MATCH("Passwords don't match", 400),
    PASSWORD_TOO_SHORT("Password must be at least 8 characters long", 400),
    PASSWORD_TOO_LONG("Password must not exceed 12 characters", 400),
    //--Validation UserUpdate
    USERNAME_MUST_BE_STRING("The value of 'username' must be a string", 400),
    NAME_MUST_BE_STRING("The value of 'name' must be a string", 400),
    EMAIL_MUST_BE_STRING("The value of 'email' must be a string", 400),
    ADDRESS_MUST_BE_STRING("The value of 'address' must be a string", 400),
    PHONE_MUST_BE_STRING("The value of 'phone' must be a string", 400),
    ROLE_MUST_BE_INTEGER("The value of 'role' must be an integer", 400),
    FIELD_NOT_RECOGNIZED("Field not recognized: ", 400),


    //--Customer
    CUSTOMER_NOT_FOUND("The customer does not exist", 404),
    CUSTOMER_ALREADY_EXISTS("The customer already exists", 409),
    CUSTOMER_LIST_EMPTY("The customer list is empty", 404),
    CUSTOMER_NOT_FOUND_WITH_ID("A client with that ID could not be found.", 404),
    TEMPORARY_NOT_TICKET("A temporary Customer cannot be create without a ticket", 404),
    CUSTOMER_ALREADY_PERMANENT("This customer is already permanent", 400),

    //--CustomerHistory
    CUSTOMER_HISTORY_NOT_FOUND("Client history not found", 404),
    CUSTOMER_HISTORY_ALREADY_EXISTS("Client history already exists", 409),


    //--Ticket
    TICKET_NOT_FOUND("Ticket not found", 404),
    AREA_NOT_FOUND("Area not found", 404),
    CATEGORY_NOT_FOUND("Category not found", 404),
    PRIORITY_NOT_FOUND("Priority not found", 404),
    STATUS_NOT_FOUND("Status not found", 404),
    TICKET_NOT_FOUND_WHIT_ID("Ticket not found whit ID", 404),
    TICKET_ALREADY_EXISTS("Ticket already exists", 409),
    TICKET_CLOSED("Ticket is already closed", 400),
    TICKET_PENDING("Ticket is still pending", 202),
    TICKET_NOT_CLIENT("Ticket cannot be created without a client", 400),
    TICKET_NOT_AREA("Ticket cannot be created without an area", 400),
    TICKET_NOT_CATEGORY("Ticket cannot be created without category", 400),
    TICKET_NOT_PRIORITY("Ticket cannot be created without priority", 400),
    TICKET_NOT_STATUS("Ticket cannot be created without status", 400),

    //--History
    TICKET_HISTORY_NOT_FOUND("Ticket history not found", 404),
    TICKET_HISTORY_ALREADY_EXISTS("Ticket history already exists", 409),

    //--TicketComment
    TICKET_COMMENT_NOT_FOUND("Ticket comment not found", 404),
    TICKET_COMMENT_ALREADY_EXISTS("Ticket comment already exists", 409),


    //--Connection
    CONNECTION_NOT_FOUND("Connection not found", 404),
    CONNECTION_ALREADY_ESTABLISHED("Connection already established", 409),
    CONNECTION_NOT_ACTIVE("Connection is not active", 400),
    BOX_AND_PORT_CANNOT_BE_NULL("Box and Port cannot be null", 400),
    CONNECTION_NO_PORT_ASSIGNED("This connection has no port assigned", 400),


    //--Box
    BOX_NOT_FOUND("Box not found", 404),
    BOX_ALREADY_OCCUPIED("Box is already occupied", 409),
    BOX_NOT_AVAILABLE("Box is not available", 400),
    NEW_BOX_IS_INNACTIVE("New box is inactive",400),
    BOX_CANNOT_BE_INACTIVE("Box cannot be inactive", 400),
    PORTS_COUNT_INVALID("The number of ports can only be 8, 16, or eventually 32", 400),
    POWER_CANNOT_BE_NULL("Power cannot be null", 400),


    //--Port
    PORT_NOT_FOUND("Port not found", 404),
    PORT_NOT_AVAILABLE("The selected port is not available", 400),
    PORTS_LIST_IS_EMPTY("The list of ports is empty", 400),
    PORTS_LIST_OR_POSITION_CANNOT_BE_NULL("Ports list or position cannot be null", 400),
    PORT_MUST_BE_IN_RANGE("Port must be in range stablished", 400);

    private final String message;
    private final int status;

    ErrorType(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
