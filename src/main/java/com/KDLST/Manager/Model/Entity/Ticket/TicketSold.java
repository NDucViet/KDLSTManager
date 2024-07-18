package com.KDLST.Manager.Model.Entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import com.KDLST.Manager.Model.Entity.User.User;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSold {
    String id;
    Ticket ticket;
    User user;
    Date usageDate;
    String barcode;
    int status;
}
