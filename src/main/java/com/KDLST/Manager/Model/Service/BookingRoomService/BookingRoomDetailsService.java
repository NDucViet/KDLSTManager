package com.KDLST.Manager.Model.Service.BookingRoomService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;

public interface BookingRoomDetailsService {
    public ArrayList<BookingRoomDetails> getAll();

    public BookingRoomDetails getById(int id);

    public boolean update(BookingRoomDetails bookingRoomDetails);

    public boolean add(BookingRoomDetails bookingRoomDetails);
}
