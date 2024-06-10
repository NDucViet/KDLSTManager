package com.KDLST.Manager.Controller;

import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    ServiceService serviceService;

    @Autowired
    ServiceTypeService serviceTypeService;

    @GetMapping("/")
    public String index(Model model){
        return "Admin/index";
    }

    @GetMapping("/getAllCustomer")
    public String getAllCustomers(Model model){
        return "Admin/customer";
    }
    @GetMapping("/getAllEmployee")
    public String getAllEmployees(Model model){
        return "Admin/employee";
    }
    @GetMapping("/getAllBlog")
    public String getAllBlogs(Model model){
        return "Admin/blog";
    }

    @GetMapping("/getAllService")
    public String getAllServices(Model model){
        return "Admin/service";
    }
    @GetMapping("/getAllFeedback")
    public String getAllFeedbacks(Model model){
        return "Admin/feedback";
    }

    @GetMapping("/getAllComment")
    public String getAllComments(Model model){
        return "Admin/comment";
    }

    @GetMapping("/getAllRoom")
    public String getAllEooms(Model model){
        return "Admin/room";
    }

    @GetMapping("/getRevenue")
    public String getRevenue(Model model){
        return "Admin/chart";
    }

    @GetMapping("/getAll")
    public String getAll(Model model){
        
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
    //     serviceService.delete(id);
    //     return "redirect:/admin/services";
    // }
    
}
