package com.KDLST.Manager.Model.Service.HotelService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.Hotel.Hotel;
import com.KDLST.Manager.Model.Repository.HotelRepository.HotelRepository;

@Service
public class HotelServiceImplement implements HotelService {

    private ArrayList<Hotel> hotelList;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel getById(int id) {
        return hotelRepository.getById(id);
    }

    @Override
    public ArrayList<Hotel> getAll() {
        this.hotelList = hotelRepository.getAll();
        return hotelList;
    }

}
