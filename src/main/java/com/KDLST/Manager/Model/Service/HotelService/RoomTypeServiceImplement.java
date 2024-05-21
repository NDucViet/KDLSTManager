package com.KDLST.Manager.Model.Service.HotelService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.Hotel.RoomType;
import com.KDLST.Manager.Model.Repository.HotelRepository.RoomTypeRepository;

@Service
public class RoomTypeServiceImplement implements RoomTypeService {
    private ArrayList<RoomType> roomTypeList;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public ArrayList<RoomType> getAll() {
        this.roomTypeList = roomTypeRepository.getAll();
        return roomTypeList;
    }

    @Override
    public RoomType getById(int id) {
        return roomTypeRepository.getById(id);
    }

}
