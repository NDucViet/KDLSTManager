package com.KDLST.Manager.Model.Service.HotelService;

import java.util.ArrayList;

import com.KDLST.Manager.Model.Entity.Hotel.RoomType;

public interface RoomTypeService {
    public ArrayList<RoomType> getAll();

    public RoomType getById(int id);
}
