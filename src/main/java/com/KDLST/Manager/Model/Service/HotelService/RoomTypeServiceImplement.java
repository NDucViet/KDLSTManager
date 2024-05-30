package com.KDLST.Manager.Model.Service.HotelService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.Hotel.RoomType;
import com.KDLST.Manager.Model.Repository.HotelRepository.RoomTypeRepository;

@Service
public class RoomTypeServiceImplement implements RoomTypeService {
    ArrayList<RoomType> roomTypeList = new ArrayList<>();
    @Autowired
    RoomTypeRepository roomTypeRepository = new RoomTypeRepository();

    public static void main(String[] args) {
        RoomTypeServiceImplement r = new RoomTypeServiceImplement();
        System.out.println(r.getAll());
    }

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
