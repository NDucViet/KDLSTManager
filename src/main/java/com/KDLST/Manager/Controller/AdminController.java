package com.KDLST.Manager.Controller;

import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsService;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BillService.BillService;
import com.KDLST.Manager.Model.Service.BillService.BillServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomServiceImplement;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeService;
import com.KDLST.Manager.Model.Service.HotelService.RoomTypeServiceImplement;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageService;
import com.KDLST.Manager.Model.Service.ImageBlogService.ImageServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.CommentService;
import com.KDLST.Manager.Model.Service.RateAFbService.CommentServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackService;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackServiceImplement;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeServiceImplement;
import com.KDLST.Manager.Model.Service.TicketService.TicketService;
import com.KDLST.Manager.Model.Service.TicketService.TicketServiceImplement;
import com.KDLST.Manager.Model.Service.UserService.UserService;
import com.KDLST.Manager.Model.Service.UserService.UserServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Entity.Hotel.RoomType;
import com.KDLST.Manager.Model.Entity.ImageBlog.Image;
import com.KDLST.Manager.Model.Entity.RateAFb.Comment;
import com.KDLST.Manager.Model.Entity.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    ArrayList<BillDetails> billDetails = new ArrayList<>();
    ArrayList<BookingRoomDetails> bookingRoomDetails = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Bill> bills = new ArrayList<>();
    ArrayList<BookingRoom> bookingRooms = new ArrayList<>();
    ArrayList<User> cList = new ArrayList<>();
    ArrayList<User> eList = new ArrayList<>();
    @Autowired
    ServiceService serviceService = new ServiceServiceImplement();
    ServiceTypeService serviceTypeService = new ServiceTypeServiceImplement();
    BillDetailsService billDetailsService = new BillDetailsServiceImplement();
    BookingRoomDetailsService bookingRoomDetailsService = new BookingRoomDetailsServiceImplement();
    TicketService ticketService = new TicketServiceImplement();
    UserService userService = new UserServiceImplement();
    BookingRoomService bookingRoomService = new BookingRoomServiceImplement();
    BillService billService = new BillServiceImplement();
    RoomTypeService roomTypeService = new RoomTypeServiceImplement();
    ImageService imageService = new ImageServiceImplement();
    FeedBackService feedBackService = new FeedBackServiceImplement();
    CommentService commentService = new CommentServiceImplement();
    // UserService userService = new UserServiceImplement();

    @GetMapping("/")
    public String index(Model model) throws JsonProcessingException {
        Map<String, Double> monthlyTotals = new LinkedHashMap<>();
        Map<String, Double> monthlyTotalsHotels = new LinkedHashMap<>();
        Map<String, Double> monthlyTotals1 = new LinkedHashMap<>();
        Map<String, Double> monthlyTotalsHotels1 = new LinkedHashMap<>();
        java.util.Date utilDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(utilDate);
        monthlyTotals1 = billDetailsService.getMonthlyRevenue(formattedDate.split("/")[2]);
        monthlyTotalsHotels1 = bookingRoomDetailsService.getMonthlyRevenue(formattedDate.split("/")[2]);
        if (monthlyTotals.size() != 12 && monthlyTotalsHotels.size() != 12) {
            for (int i = 1; i < 13; i++) {
                String key = String.valueOf(i);
                if (!monthlyTotals1.containsKey(key)) {
                    monthlyTotals.put(key, 0.0);

                } else {
                    monthlyTotals.put(key, monthlyTotals1.get(key));
                }
                if (!monthlyTotalsHotels1.containsKey(key)) {
                    monthlyTotalsHotels.put(key, 0.0);
                } else {
                    monthlyTotalsHotels.put(key, monthlyTotalsHotels1.get(key));
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMonthlyTotals = objectMapper.writeValueAsString(monthlyTotals);
        String jsonMonthlyTotals1 = objectMapper.writeValueAsString(monthlyTotalsHotels);

        bookingRooms = bookingRoomService.getAll();
        users = userService.getAll();
        bills = billService.getAll();
        // employee, customer
        int customer = 0;
        int employee = 0;
        for (User user : users) {
            if (user.getRole().equals("CUSTOMER")) {
                customer += 1;
                if (cList.size() < 7) {
                    cList.add(user);
                }

            } else if (user.getRole().equals("EMPLOYEE")) {
                employee += 1;
                if (eList.size() < 7) {
                    eList.add(user);
                }
            }
        }

        // service
        ArrayList<Services> sList = serviceService.getAll();
        ArrayList<Services> sLists = new ArrayList<>();
        for (Services s : sList) {
            if (sLists.size() < 7) {
                sLists.add(s);
            }
        }

        // ticket
        ArrayList<Ticket> ticketList = ticketService.getAll();
        ArrayList<Ticket> ticketLists = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            if (ticketLists.size() < 7) {
                ticketLists.add(ticket);
            }
        }

        // blog
        ArrayList<Image> imgList = imageService.getAll();
        Set<Image> images = new HashSet<>();
        for (Image image : imgList) {
            images.add(image);
            if (images.size() == 6) {
                break;
            }
        }

        // room
        ArrayList<RoomType> rList = roomTypeService.getAll();

        // feedback
        // ArrayList<FeedBack> fList = feedBackService.getAll();
        // ArrayList<FeedBack> fLists = new ArrayList<>();
        // if (fList != null) {
        // for (int i = 0; i < 6; i++) {
        // fLists.add(fList.get(i));
        // }
        // }

        // comment
        ArrayList<Comment> comList = commentService.getAll();
        ArrayList<Comment> comListLists = new ArrayList<>();
        for (Comment comment : comList) {
            if (comListLists.size() < 7) {
                comListLists.add(comment);
            }
        }
        model.addAttribute("data2", jsonMonthlyTotals1);
        model.addAttribute("data1", jsonMonthlyTotals);
        model.addAttribute("service", serviceService.getAll().size());
        model.addAttribute("order", bills.size() + bookingRooms.size());
        model.addAttribute("data2", jsonMonthlyTotals1);
        model.addAttribute("data1", jsonMonthlyTotals);
        model.addAttribute("customer", customer);
        model.addAttribute("employee", employee);
        model.addAttribute("cList", cList);
        model.addAttribute("eList", eList);
        model.addAttribute("serviceList", sLists);
        // model.addAttribute("feedbackList", fLists);
        model.addAttribute("commentList", comListLists);
        model.addAttribute("roomTypeList", rList);
        model.addAttribute("ticketList", ticketLists);
        model.addAttribute("blogList", images);

        return "Admin/index";
    }

    // ArrayList<User> cList = new ArrayList<>();

    // @Autowired
    // TicketService ticketService = new TicketServiceImplement();

    @GetMapping("/getAllCustomer")
    public String getAllCustomer(Model model) {
        cList = userService.getAllCustomer();
        return getPageCustomer(model, "1");
    }

    @GetMapping("/getAllCustomer/{page}")
    public String getPageCustomer(Model model, @PathVariable(value = "page") String currentPage) {
        ArrayList<User> customerList = new ArrayList<>();
        int customerPage = 5;
        int numPages = (int) Math.ceil((float) cList.size() / customerPage);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }
        for (int i = (Integer.parseInt(currentPage) - 1) * customerPage; i < Integer.parseInt(currentPage)
                * customerPage; i++) {
            if (cList.size() <= i)
                break;
            customerList.add(cList.get(i));
        }
        model.addAttribute("customerList", customerList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "Admin/customer";
    }

    // @GetMapping("/getAllCustomer")
    // public String getAllCustomers(Model model) {
    // // ArrayList<Customer> cList

    // return "Admin/customer";
    // }

    @GetMapping("/getAllEmployee")
    public String getAllEmployees(Model model) {
        return "Admin/employee";
    }

    @GetMapping("/getAllBlog")
    public String getAllBlogs(Model model) {
        return "Admin/blog";
    }

    @GetMapping("/getAllService")
    public String getAllServices(Model model) {
        return "Admin/service";
    }

    @GetMapping("/getAllFeedback")
    public String getAllFeedbacks(Model model) {
        return "Admin/feedback";
    }

    @GetMapping("/getAllComment")
    public String getAllComments(Model model) {
        return "Admin/comment";
    }

    @GetMapping("/getAllRoom")
    public String getAllEooms(Model model) {
        return "Admin/room";
    }

    @GetMapping("/getRevenue")
    public String getRevenue(Model model) throws JsonProcessingException {
        ArrayList<BookingRoomDetails> b = bookingRoomDetailsService.getAll();
        ArrayList<BillDetails> billDetails = billDetailsService.getAll();
        Map<String, Double> totalsMap = new LinkedHashMap<>();
        Map<String, Double> pieMap = new LinkedHashMap<>();
        Map<String, Double> hotelTotals = bookingRoomDetailsService.getYearsRevenue();
        Map<String, Double> serviceTotals = billDetailsService.getYearsRevenue();
        Map<String, Double> roomTypeMap = new LinkedHashMap<>();
        Map<String, Double> ticketMap = new LinkedHashMap<>();
        ArrayList<Ticket> tickets = ticketService.getAll();
        for (Ticket ticket : tickets) {
            ticketMap.put(ticket.getTitle(), 0.0);
        }
        for (BillDetails bDetails : billDetails) {
            String key = bDetails.getTicketID().getTitle();
            double value = ticketMap.get(key) + bDetails.getTotal();
            ticketMap.put(key, value);
        }
        roomTypeMap.put("Luxurious Room", 0.0);
        roomTypeMap.put("Family Room", 0.0);
        roomTypeMap.put("Couple Room", 0.0);
        roomTypeMap.put("Normal Room", 0.0);
        roomTypeMap.put("President Room", 0.0);
        for (BookingRoomDetails bookingRoomDetails : b) {
            if (roomTypeMap.containsKey(bookingRoomDetails.getRoom().getRoomType().getRoomTypeName())) {
                String key = bookingRoomDetails.getRoom().getRoomType().getRoomTypeName();
                Double value = bookingRoomDetails.getRoom().getRoomType().getPrice() + roomTypeMap.get(key);
                roomTypeMap.put(key, value);
            }
        }
        double pieHotel = 0.0;
        double pieService = 0.0;
        System.out.println(hotelTotals.toString());
        System.out.println(serviceTotals.toString());
        for (Map.Entry<String, Double> entry : hotelTotals.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            totalsMap.put(key, value);
            pieHotel += value;
        }
        for (Map.Entry<String, Double> entry : serviceTotals.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            if (totalsMap.containsKey(key)) {
                totalsMap.put(key, totalsMap.get(key) + value);
                pieService += value;
            } else {
                totalsMap.put(key, value);
                pieService += value;
            }
        }
        double totals = 0.0;
        for (Map.Entry<String, Double> entry : roomTypeMap.entrySet()) {
            totals += entry.getValue();
        }
        for (Map.Entry<String, Double> entry : roomTypeMap.entrySet()) {
            entry.setValue((entry.getValue() * 100) / totals);
        }
        double totals1 = 0.0;
        for (Map.Entry<String, Double> entry : ticketMap.entrySet()) {
            totals1 += entry.getValue();
        }
        for (Map.Entry<String, Double> entry : ticketMap.entrySet()) {
            entry.setValue((entry.getValue() * 100) / totals1);
        }
        pieMap.put("hotel", (pieHotel * 100) / (pieHotel + pieService));
        pieMap.put("service", 100 - ((pieHotel * 100) / (pieHotel + pieService)));
        model.addAttribute("ticket", ticketMap);
        model.addAttribute("roomType", roomTypeMap);
        model.addAttribute("totalsMap", totalsMap);
        model.addAttribute("pieMap", pieMap);

        return "Admin/chart";
    }

    // @PostMapping("/delete")
    // public String deleteService(@RequestParam("id") int id) {
    // serviceService.delete(id);
    // return "redirect:/admin/services";
    // }

}
