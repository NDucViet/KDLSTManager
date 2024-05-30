
package com.KDLST.Manager.Model.Entity.Bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetails {
    private Bill billID;
    private int ticketID;
    private int quantity;
    private double total;
}