package com.KDLST.Manager.Model.Entity.Hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private int roomID;
    private Hotel hotel;
    private RoomType roomType;
    private boolean status;

}
