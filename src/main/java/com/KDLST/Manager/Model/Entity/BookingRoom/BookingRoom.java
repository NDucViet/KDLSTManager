package com.KDLST.Manager.Model.Entity.BookingRoom;

import com.KDLST.Manager.Model.Entity.User.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRoom {
    private int bookingRoomID;
    private User user;
    private Date startDate;
    private Date endDate;
    private boolean status;
}
