package com.KDLST.Manager.Model.Entity.BookingRoom;

import com.KDLST.Manager.Model.Entity.Hotel.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRoomDetails {
    private int bookingRoomDetailsID;
    private BookingRoom bookingRoom;
    private Room room;
}
