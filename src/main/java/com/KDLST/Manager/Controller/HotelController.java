package com.KDLST.Manager.Controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Entity.Hotel.Room;
import com.KDLST.Manager.Model.Entity.Hotel.RoomType;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomServiceImplement;
import com.KDLST.Manager.Model.Service.CartItemService.VNPayService;
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
    private RoomService roomService = new RoomServiceImplement();
    private RoomTypeServiceImplement roomTypeService = new RoomTypeServiceImplement();
    private BookingRoomDetailsService bookingRoomDetailsService = new BookingRoomDetailsServiceImplement();
    private BookingRoomService bookingroomService = new BookingRoomServiceImplement();
    private VNPayService vnPayService = new VNPayService();

    @GetMapping({ "/", "" })
    public String indexHotel(Model model) {
        ArrayList<RoomType> roomTypeList = roomTypeService.getAll();
        model.addAttribute("roomTypeList", roomTypeList);
        return "Hotel/index";
    }

    @GetMapping({ "/about" })
    public String about() {
        return "Hotel/about";
    }

    @GetMapping({ "/contactUs" })
    public String contactUs() {
        return "Hotel/contact";
    }

    @GetMapping({ "/getDate" })
    public String getDate() {
        return "Hotel/getDate";
    }

    @GetMapping({ "/getRoom" })
    public String getRoom(@RequestParam(name = "roomType") int id, Model model) {
        model.addAttribute("id", id);
        return "Hotel/getDate";
    }

    @GetMapping({ "/getRoomType" })
    public String getRoomType(Model model) {
        ArrayList<RoomType> roomTypeList = roomTypeService.getAll();
        model.addAttribute("roomTypeList", roomTypeList);
        return "Hotel/getRoomType";
    }

    @GetMapping("/getRoomByRoomType")
    public String bookRoomByRoomType(Model model, @RequestParam(name = "roomType") int id,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate) {
        List<Room> roomList = roomService.getByIdRoomType(id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        java.util.Date utilDate;
        System.out.println(startDate);
        Date sqlStartDate = new Date(0);
        List<BookingRoomDetails> bookingRoomDetailsList = bookingRoomDetailsService.getAll();
        HashSet<Room> setRoom = new HashSet<>();
        try {
            utilDate = dateFormat.parse(startDate);
            sqlStartDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println(e);
        }
        for (BookingRoomDetails bookingRoom : bookingRoomDetailsList) {
            if (bookingRoom.getBookingRoom() == null || bookingRoom.getBookingRoom().isStatus() == false) {
                continue;
            }
            if (!(sqlStartDate.compareTo(bookingRoom.getBookingRoom().getEndDate()) >= 0)) {
                RoomType roomType = bookingRoom.getRoom().getRoomType();
                if (roomType != null && roomType.getRoomTypeID() == id) {
                    roomList.remove(bookingRoom.getRoom());
                }
            }
        }
        setRoom.addAll(roomList);
        List<Room> roomListss = new ArrayList<>(setRoom);
        HashMap<List<Room>, String> roomHashMap = new HashMap<>();

        if (!roomListss.isEmpty() && roomListss.get(0).getRoomType() != null) {
            roomHashMap.put(roomListss, roomListss.get(0).getRoomType().getRoomTypeName());
        } else {
            // Handle the case where roomListss is empty or the room type is null
            roomHashMap.put(roomListss, "Không xác định được loại phòng");
        }

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("setRoom", roomHashMap);
        return "Hotel/bookRoomValid";
    }

    @GetMapping("/getAllRoom")
    public String getAllRoom(Model model, @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        java.util.Date utilDate;
        Date sqlStartDate = new Date(0);
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
                if (bookingRoom.getBookingRoom() == null || bookingRoom.getBookingRoom().isStatus() == false) {
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
        return "Hotel/bookRoomValid";
    }

    @GetMapping("/history")
    public String getHistory(Model model, HttpServletRequest request) {
        Map<ArrayList<BookingRoomDetails>, String> boArrayList = new LinkedHashMap<>();

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

        return "Hotel/history";
    }

    @GetMapping("/searchRoomType")
    public String searchRoomType(Model model, @RequestParam("keyword") String keyword) {
        ArrayList<RoomType> roomTypeList = roomTypeService.searchRoomType(keyword);
        model.addAttribute("roomTypeList", roomTypeList);
        return "Hotel/getRoomType";
    }

    @GetMapping(value = "/roomTypeSuggestion")
    public ResponseEntity<ArrayList<RoomType>> roomTypeSuggestion(@RequestParam("keyword") String keyword) {
        ArrayList<RoomType> roomTypeList = roomTypeService.searchRoomType(keyword);
        return ResponseEntity.ok().body(roomTypeList);
    }

    @PostMapping("/checkOut")
    public String checkOut(@RequestParam(name = "bookingRoom") String bookingRoom,
            HttpServletRequest request, @RequestParam(name = "date") String date,
            @RequestParam(name = "total") String price) {
        String info = String.join("|", bookingRoom, date);
        System.out.println(price);
        System.out.println(info);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, Integer.parseInt(price), info,
                baseUrl + "/hotel/vnpay-payment-return");
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        java.util.Date utilDate = new java.util.Date(0);
        java.util.Date utilDate1 = new java.util.Date(0);
        Date sqlStartDate = new Date(0);
        Date sqlEndDate = new Date(0);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        if (paymentStatus == 1) {
            System.out.println(orderInfo);
            String info[] = orderInfo.split("\\|");

            // lấy ra id các phòng đã đặt
            String roomsBooking[] = info[0].split(",");

            // lấy ra ngày đặt
            String date[] = info[1].split(";");
            String startDate = date[0];
            String endtDate = date[1];
            try {
                utilDate1 = dateFormat.parse(endtDate);
                utilDate = dateFormat.parse(startDate);
                sqlStartDate = new Date(utilDate.getTime());
                sqlEndDate = new Date(utilDate1.getTime());
            } catch (ParseException e) {
                System.out.println(e);
            }
            // Tạo mới các bookingRoom và bookingRoomDetail
            HttpSession session = request.getSession();
            User userSession = (User) session.getAttribute("user");
            BookingRoom bookingRoom = new BookingRoom(0, userSession, sqlStartDate, sqlEndDate, true);
            if (bookingroomService.add(bookingRoom)) {
                ArrayList<BookingRoom> bolist = bookingroomService.getByIdUser(userSession.getIdUser());
                bookingRoom = bolist.get(bolist.size() - 1);
                for (int i = 0; i < roomsBooking.length; i++) {
                    Room room = roomService.getById(Integer.parseInt(roomsBooking[i]));
                    LocalDate startLocalDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate endLocalDate = utilDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    BookingRoomDetails bookingRoomDetails = new BookingRoomDetails(i, bookingRoom,
                            room,
                            ChronoUnit.DAYS.between(startLocalDate, endLocalDate) * room.getRoomType().getPrice());
                    System.out.println(bookingRoomDetailsService.add(bookingRoomDetails));
                    ;
                }
            }
            // ....
        }
        model.addAttribute("orderId", "Đặt phòng khách sạn");
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1) {
            return "Hotel/ordersuccess";
        } else {
            return "Hotel/orderfail";
        }
    }
}