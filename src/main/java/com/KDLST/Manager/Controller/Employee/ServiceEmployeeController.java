package com.KDLST.Manager.Controller.Employee;

import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/employee/services")
public class ServiceEmployeeController {
    @Autowired
    ServiceService serviceService;

    @Autowired
    ServiceTypeService serviceTypeService;

    @GetMapping
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView("/Employee/serviceEmployee");
        modelAndView.addObject("services", serviceService.getAll());
        modelAndView.addObject("serviceTypes", serviceTypeService.getAll());
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateService(@ModelAttribute Services service, @RequestParam int typeID) {
        ServiceType serviceType = new ServiceType();
        serviceType.setServiceTypeID(typeID);
        service.setServiceTypeID(serviceType);
        serviceService.update(service);
        return "redirect:/employee/services";
    }

}

