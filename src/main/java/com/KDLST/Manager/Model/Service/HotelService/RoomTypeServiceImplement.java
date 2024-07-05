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

    @Override
    public ArrayList<RoomType> getAll() {
        this.roomTypeList = roomTypeRepository.getAll();
        return roomTypeList;
    }

    @Override
    public RoomType getById(int id) {
        return roomTypeRepository.getById(id);
    }

    @Override
    public boolean update(RoomType type) {
        if (roomTypeRepository.update(type)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<RoomType> searchRoomType(String keyword) {
        ArrayList<RoomType> filterRoomType = new ArrayList<>();
        getAll();
        for (RoomType roomType : roomTypeList) {
            if (roomType.getRoomTypeName().toLowerCase().contains(keyword.toLowerCase())) {
                filterRoomType.add(roomType);
            }
        }
        return filterRoomType;
    }
}