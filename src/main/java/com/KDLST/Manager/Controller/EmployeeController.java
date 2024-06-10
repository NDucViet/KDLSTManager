package com.KDLST.Manager.Controller;

import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    ServiceService serviceService;

    @Autowired
    ServiceTypeService serviceTypeService;

     @GetMapping("/")
    public String index(Model model){
        return "Employee/index";
    }

    @GetMapping("/getAllCustomer")
    public String getAllCustomers(Model model){
        return "Employee/customer";
    }
    // @GetMapping("/")
    // public ModelAndView getAll(){
    //     ModelAndView modelAndView = new ModelAndView("/Employee/serviceEmployee");
    //     modelAndView.addObject("services", serviceService.getAll());
    //     modelAndView.addObject("serviceTypes", serviceTypeService.getAll());
    //     return modelAndView;
    // }

    // @PostMapping("/update")
    // public String updateService(@ModelAttribute Services service, @RequestParam int typeID) {
    //     ServiceType serviceType = new ServiceType();
    //     serviceType.setServiceTypeID(typeID);
    //     service.setServiceTypeID(serviceType);
    //     serviceService.update(service);
    //     return "redirect:/employee/services";
    // }

}

