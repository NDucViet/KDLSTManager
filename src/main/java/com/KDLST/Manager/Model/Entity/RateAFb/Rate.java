package com.KDLST.Manager.Model.Entity.RateAFb;

import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rate {
    private int rateID;
    private User user;
    private int amountStar;
    private Ticket ticket;
}
