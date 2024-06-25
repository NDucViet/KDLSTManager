package com.KDLST.Manager.Controller;

import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsService;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeServiceImplement;
import com.KDLST.Manager.Model.Service.TicketService.TicketService;
import com.KDLST.Manager.Model.Service.TicketService.TicketServiceImplement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    ArrayList<BillDetails> billDetails = new ArrayList<>();
    ArrayList<BookingRoomDetails> bookingRoomDetails = new ArrayList<>();

    @Autowired
    ServiceService serviceService = new ServiceServiceImplement();
    ServiceTypeService serviceTypeService = new ServiceTypeServiceImplement();
    BillDetailsService billDetailsService = new BillDetailsServiceImplement();
    BookingRoomDetailsService bookingRoomDetailsService = new BookingRoomDetailsServiceImplement();
    TicketService ticketService = new TicketServiceImplement();

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
        model.addAttribute("data2", jsonMonthlyTotals1);
        model.addAttribute("data1", jsonMonthlyTotals);
        return "Admin/index";
    }

    @GetMapping("/getAllCustomer")
    public String getAllCustomers(Model model) {
        return "Admin/customer";
    }

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

    @GetMapping("/getAll")
    public String getAll(Model model) {

        model.addAttribute("services", serviceService.getAll());
        model.addAttribute("serviceTypes", serviceTypeService.getAll());
        return "serviceAdmin";
    }

    @PostMapping("/add")
    public String addService(@ModelAttribute Services service, @RequestParam int typeID) {
        ServiceType serviceType = new ServiceType();
        serviceType.setServiceTypeID(typeID);
        service.setServiceTypeID(serviceType);
        serviceService.add(service);
        return "redirect:/admin/services";
    }

    @PostMapping("/update")
    public String updateService(@ModelAttribute Services service, @RequestParam int typeID) {
        ServiceType serviceType = new ServiceType();
        serviceType.setServiceTypeID(typeID);
        service.setServiceTypeID(serviceType);
        serviceService.update(service);
        return "redirect:/admin/services";
    }

    // @PostMapping("/delete")
    // public String deleteService(@RequestParam("id") int id) {
    // serviceService.delete(id);
    // return "redirect:/admin/services";
    // }

}
