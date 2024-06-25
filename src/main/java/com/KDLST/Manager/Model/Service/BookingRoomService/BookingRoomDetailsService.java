package com.KDLST.Manager.Model.Service.BookingRoomService;

import java.util.ArrayList;
import java.util.Map;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;

public interface BookingRoomDetailsService {
    public ArrayList<BookingRoomDetails> getAll();

    public BookingRoomDetails getById(int id);

    public Map<String, Double> getMonthlyRevenue(String year);

    public ArrayList<BookingRoomDetails> getByIDRoomDetails(int id);

    public Map<String, Double> getYearsRevenue();

    public boolean update(BookingRoomDetails bookingRoomDetails);

    public boolean add(BookingRoomDetails bookingRoomDetails);

}
