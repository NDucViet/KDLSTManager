
package com.KDLST.Manager.Model.Entity.Bill;

import com.KDLST.Manager.Model.Entity.Ticket.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetails {
    private int billDetailsID;
    private Bill billID;
    private Ticket ticketID;
    private int quantity;
    private double total;
}