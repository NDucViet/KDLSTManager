package com.KDLST.Manager.Model.Service.HotelService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.Hotel.Room;

public interface RoomService {
    public ArrayList<Room> getAll();

    public Room getById(int id);

    public boolean update(Room room);
}
