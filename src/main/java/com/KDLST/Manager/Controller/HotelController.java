package com.KDLST.Manager.Controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.Map;

import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Entity.Hotel.Room;
import com.KDLST.Manager.Model.Entity.Hotel.RoomType;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomServiceImplement;
import com.KDLST.Manager.Model.Service.HotelService.HotelService;
import com.KDLST.Manager.Model.Service.HotelService.HotelServiceImplement;
import com.KDLST.Manager.Model.Service.HotelService.RoomService;
import com.KDLST.Manager.Model.Service.HotelService.RoomServiceImplement;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeServiceImplement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {
    ArrayList<BookingRoomDetails> bookingRoomDetailsList = new ArrayList<>();
    ArrayList<Room> roomList = new ArrayList<>();
    @Autowired
    private HotelService hotelService = new HotelServiceImplement();
    private RoomService roomService = new RoomServiceImplement();
    private RoomTypeServiceImplement roomTypeService = new RoomTypeServiceImplement();
    private BookingRoomDetailsService bookingRoomDetailsService = new BookingRoomDetailsServiceImplement();
    private BookingRoomService bookingroomService = new BookingRoomServiceImplement();

    @GetMapping({ "/", "" })
    public String indexHotel() {
        return "index";
    }

    @GetMapping({ "/about" })
    public String about() {
        return "about";
    }

    @GetMapping({ "/contactUs" })
    public String contactUs() {
        return "contact";
    }

    @GetMapping({ "/getDate" })
    public String getDate() {
        return "getDate";
    }

    @GetMapping({ "/getRoom" })
    public String getRoom(@RequestParam(name = "roomType") int id, Model model) {
        model.addAttribute("id", id);
        return "getDate";
    }

    @GetMapping({ "/getRoomType" })
    public String getRoomType(Model model) {
        ArrayList<RoomType> roomTypeList = roomTypeService.getAll();
        model.addAttribute("roomTypeList", roomTypeList);
        return "getRoomType";
    }

    @GetMapping("/getRoomByRoomType")
    public String bookRoomByRoomType(Model model, @RequestParam(name = "roomType") int id,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate) {
        roomList = roomService.getByIdRoomType(id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        java.util.Date utilDate;
        System.out.println(startDate);
        Date sqlStartDate = null;
        bookingRoomDetailsList = bookingRoomDetailsService.getAll();
        HashSet<Room> setRoom = new HashSet<>();
        try {
            utilDate = dateFormat.parse(startDate);
            sqlStartDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println(e);
        }
        for (BookingRoomDetails bookingRoom : bookingRoomDetailsList) {
            if (bookingRoom.getBookingRoom() == null) {
                continue;
            }
            if (sqlStartDate.compareTo(bookingRoom.getBookingRoom().getEndDate()) >= 0
                    && bookingRoom.getRoom().getRoomType().getRoomTypeID() == id) {
                setRoom.add(bookingRoom.getRoom());
            }
        }
        setRoom.addAll(roomList);
        List<Room> roomListss = new ArrayList<>(setRoom);
        HashMap<List<Room>, String> rommHashMap = new HashMap<>();
        rommHashMap.put(roomListss, roomListss.get(0).getRoomType().getRoomTypeName());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("setRoom", rommHashMap);
        return "bookRoomValid";
    }

    @GetMapping("/getAllRoom")
    public String getAllRoom(Model model, @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        java.util.Date utilDate;
        Date sqlStartDate = null;
        bookingRoomDetailsList = bookingRoomDetailsService.getAll();
        HashMap<List<Room>, String> hashRoom = new HashMap<>();
        try {
            utilDate = dateFormat.parse(startDate);
            sqlStartDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println(e);
        }
        for (RoomType roomType : roomTypeService.getAll()) {
            HashSet<Room> setRoom = new HashSet<>();
            roomList = roomService.getByIdRoomType(roomType.getRoomTypeID());
            for (BookingRoomDetails bookingRoom : bookingRoomDetailsList) {
                if (bookingRoom.getBookingRoom() == null) {
                    continue;
                }
                if (!(sqlStartDate.compareTo(bookingRoom.getBookingRoom().getEndDate()) >= 0
                        && bookingRoom.getRoom().getRoomType().getRoomTypeID() == roomType.getRoomTypeID())) {
                    roomList.remove(bookingRoom.getRoom());
                }
            }
            setRoom.addAll(roomList);
            List<Room> rooms = new ArrayList<>(setRoom);
            hashRoom.put(rooms, roomType.getRoomTypeName());
        }
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("setRoom", hashRoom);
        return "bookRoomValid";
    }

    @GetMapping("/history")
    public String getHistory(Model model, HttpServletRequest request) {
        Map<ArrayList<BookingRoomDetails>, String> boArrayList = new HashMap<>();

        // hashmap ArrayList<BookingRoomDetails>// date
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        ArrayList<BookingRoom> bookingRoom = bookingroomService.getByIdUser(user.getIdUser());
        for (BookingRoom bookingRoom2 : bookingRoom) {
            ArrayList<BookingRoomDetails> bookingRoomDetailListt = bookingRoomDetailsService
                    .getByIDRoomDetails(bookingRoom2.getBookingRoomID());
            boArrayList.put(bookingRoomDetailListt, bookingRoom2.getStartDate() + "-" + bookingRoom2.getEndDate());
        }
        model.addAttribute("history", boArrayList);

        return "history";
    }

}
