package com.KDLST.Manager.Model.Service.BookingRoomService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Repository.BookingRoomRepository.BookingRoomRepository;

@Service
public class BookingRoomServiceImplement implements BookingRoomService {
    ArrayList<BookingRoom> bookingRoomList = new ArrayList<>();

    @Autowired
    BookingRoomRepository bookingRoomRepository = new BookingRoomRepository();

    @Override
    public ArrayList<BookingRoom> getAll() {
        this.bookingRoomList = bookingRoomRepository.getAll();
        return bookingRoomList;
    }

    @Override
    public BookingRoom getById(int id) {
        return bookingRoomRepository.getById(id);
    }

    @Override
    public boolean update(BookingRoom bookingRoom) {
        if (bookingRoomRepository.update(bookingRoom)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(BookingRoom bookingRoom) {
        if (bookingRoomRepository.add(bookingRoom)) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<BookingRoom> getByStatus() {
        return bookingRoomRepository.getByStatus();
    }

    @Override
    public ArrayList<BookingRoom> getByIdUser(int id) {
        return bookingRoomRepository.getByIdUser(id);
    }
}
