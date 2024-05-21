package com.KDLST.Manager.Model.Service.BookingRoomService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Repository.BookingRoomRepository.BookingRoomDetailsRepository;

@Service
public class BookingRoomDetailsServiceImplement implements BookingRoomDetailsService {
    private ArrayList<BookingRoomDetails> bookingRoomDetailList;
    @Autowired
    private BookingRoomDetailsRepository bookingRoomDetailsRepository;

    @Override
    public ArrayList<BookingRoomDetails> getAll() {
        this.bookingRoomDetailList = bookingRoomDetailsRepository.getAll();
        return bookingRoomDetailList;
    }

    @Override
    public BookingRoomDetails getById(int id) {
        return bookingRoomDetailsRepository.getById(id);
    }

    @Override
    public boolean update(BookingRoomDetails bookingRoomDetails) {
        if (bookingRoomDetailsRepository.update(bookingRoomDetails)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(BookingRoomDetails bookingRoomDetails) {
        if (bookingRoomDetailsRepository.add(bookingRoomDetails)) {
            return true;
        }
        return false;
    }

}
