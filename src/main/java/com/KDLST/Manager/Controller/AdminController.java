package com.KDLST.Manager.Controller;

import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoom;
import com.KDLST.Manager.Model.Entity.BookingRoom.BookingRoomDetails;
import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsService;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BillService.BillService;
import com.KDLST.Manager.Model.Service.BillService.BillServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomService;
import com.KDLST.Manager.Model.Service.BookingRoomService.BookingRoomServiceImplement;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeServiceImplement;
import com.KDLST.Manager.Model.Service.UserService.UserService;
import com.KDLST.Manager.Model.Service.UserService.UserServiceImplement;
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
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Bill> bills = new ArrayList<>();
    ArrayList<BookingRoom> bookingRooms = new ArrayList<>();

    @Autowired
    ServiceService serviceService = new ServiceServiceImplement();
    ServiceTypeService serviceTypeService = new ServiceTypeServiceImplement();
    BillDetailsService billDetailsService = new BillDetailsServiceImplement();
    BookingRoomDetailsService bookingRoomDetailsService = new BookingRoomDetailsServiceImplement();
    UserService userService = new UserServiceImplement();
    BookingRoomService bookingRoomService = new BookingRoomServiceImplement();
    BillService billService = new BillServiceImplement();

    @GetMapping("/")
    public String index(Model model) throws JsonProcessingException {
        billDetails = billDetailsService.getAll();
        bookingRoomDetails = bookingRoomDetailsService.getAll();
        bookingRooms = bookingRoomService.getAll();
        users = userService.getAll();
        bills = billService.getAll();
        Map<String, Double> monthlyTotalsHotels = new LinkedHashMap<>();
        Map<String, Double> monthlyTotals = new LinkedHashMap<>();
        java.util.Date utilDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(utilDate);
        for (int i = 1; i < 13; i++) {
            monthlyTotals.put(i + "", 0.0);
            monthlyTotalsHotels.put(i + "", 0.0);
        }
        for (BillDetails billDetail : billDetails) {
            String month = billDetail.getBillID().getDatePay().toString();
            if (month.split("-")[0].equals(formattedDate.split("/")[2])) {
                String monthNow[] = month.split("-");
                for (Map.Entry<String, Double> entry : monthlyTotals.entrySet()) {
                    if (Integer.parseInt(monthNow[1]) == Integer.parseInt((entry.getKey()))) {
                        Double price = entry.getValue() + billDetail.getTotal();
                        entry.setValue(price);
                    }
                }

            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMonthlyTotals = objectMapper.writeValueAsString(monthlyTotals);
        for (BookingRoomDetails bookingRoomDetail : bookingRoomDetails) {
            String month1 = bookingRoomDetail.getBookingRoom().getStartDate().toString();
            if (month1.split("-")[0].equals(formattedDate.split("/")[2])) {
                String monthNow[] = month1.split("-");
                for (Map.Entry<String, Double> entry : monthlyTotalsHotels.entrySet()) {
                    if (Integer.parseInt(monthNow[1]) == Integer.parseInt((entry.getKey()))) {
                        Double price = entry.getValue() + bookingRoomDetail.getTotals();
                        entry.setValue(price);
                    }
                }

            }
        }
        int customer = 0;
        int employee = 0;
        for (User user : users) {
            if (user.getRole().equals("CUSTOMER")) {
                customer += 1;
            } else if (user.getRole().equals("EMPLOYEE")) {
                employee += 1;
            }
        }
        String jsonMonthlyTotals1 = objectMapper.writeValueAsString(monthlyTotalsHotels);
        model.addAttribute("service", serviceService.getAll().size());
        model.addAttribute("order", bills.size() + bookingRooms.size());
        model.addAttribute("data2", jsonMonthlyTotals1);
        model.addAttribute("data1", jsonMonthlyTotals);
        model.addAttribute("customer", customer);
        model.addAttribute("employee", employee);
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
    public String getRevenue(Model model) {
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
