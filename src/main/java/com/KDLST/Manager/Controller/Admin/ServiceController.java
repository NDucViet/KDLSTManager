package com.KDLST.Manager.Controller.Admin;

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
public class ServiceController {
    @Autowired
    ServiceService serviceService;

    @Autowired
    ServiceTypeService serviceTypeService;

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
