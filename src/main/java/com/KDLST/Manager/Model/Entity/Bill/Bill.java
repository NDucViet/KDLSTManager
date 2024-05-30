package com.KDLST.Manager.Model.Entity.Bill;

import java.sql.Date;
import com.KDLST.Manager.Model.Entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private int billID;
    private User user;
    private Date datePay;
    private boolean status;
}
