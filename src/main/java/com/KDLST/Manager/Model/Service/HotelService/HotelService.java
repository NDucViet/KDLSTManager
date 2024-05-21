package com.KDLST.Manager.Model.Service.HotelService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.Hotel.Hotel;

public interface HotelService {
    public Hotel getById(int id);

    public ArrayList<Hotel> getAll();
}
