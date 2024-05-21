package com.KDLST.Manager.Model.Service.BookingRoomService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;

public interface BookingRoomService {
    public ArrayList<BookingRoom> getAll();

    public BookingRoom getById(int id);

    public boolean update(BookingRoom bookingRoom);

    public boolean add(BookingRoom bookingRoom);
}
