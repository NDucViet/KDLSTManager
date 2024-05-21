package com.KDLST.Manager.Model.Entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
    private int ticketTypeID;
    private String ticketTypeName;

}