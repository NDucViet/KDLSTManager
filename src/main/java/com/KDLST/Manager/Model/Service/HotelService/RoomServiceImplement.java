package com.KDLST.Manager.Model.Service.HotelService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.Hotel.Room;
import com.KDLST.Manager.Model.Repository.HotelRepository.RoomRepository;

@Service
public class RoomServiceImplement implements RoomService {
    private ArrayList<Room> roomList;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ArrayList<Room> getAll() {
        this.roomList = roomRepository.getAll();
        return roomList;
    }

    @Override
    public Room getById(int id) {
        return roomRepository.getById(id);
    }

    @Override
    public boolean update(Room room) {
        if (roomRepository.update(room)) {
            return true;
        }
        return false;
    }

}
