package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;
import java.util.ArrayList;

@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceService service = new ServiceServiceImplement();

    @GetMapping("/getAll")
    public String getAll(Model model) {
        ArrayList<Services> serviceList = service.getAll();
        model.addAttribute("serviceList", serviceList);
        return "User/service";
    }
}
