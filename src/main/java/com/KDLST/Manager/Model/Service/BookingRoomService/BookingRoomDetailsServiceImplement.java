package com.KDLST.Manager.Model.Service.BookingRoomService;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Repository.BookingRoomRepository.BookingRoomDetailsRepository;

@Service
public class BookingRoomDetailsServiceImplement implements BookingRoomDetailsService {
    ArrayList<BookingRoomDetails> bookingRoomDetailList = new ArrayList<>();
    @Autowired
    BookingRoomDetailsRepository bookingRoomDetailsRepository = new BookingRoomDetailsRepository();

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

    @Override
    public ArrayList<BookingRoomDetails> getByIDRoomDetails(int id) {
        return bookingRoomDetailsRepository.getByIDRoomDetails(id);
    }

    @Override
    public Map<String, Double> getMonthlyRevenue(String year) {
        // TODO Auto-generated method stub
        return bookingRoomDetailsRepository.getMonthlyRevenue(year);
    }

    @Override
    public Map<String, Double> getYearsRevenue() {
        return bookingRoomDetailsRepository.getYearsRevenue();
    }

}
