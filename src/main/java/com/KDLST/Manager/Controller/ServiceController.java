package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getAll(Model model, @RequestParam(name="index", defaultValue = "1") int index, 
    @RequestParam(name="serviceTypeID", defaultValue ="1") int serviceID ) {
        ArrayList<Services> serviceList = service.getPageImage(1,1);
        int endPage =1;
        if(serviceList !=null){
            int count = serviceList.size();
            endPage = count/6;
            if(count %6 != 0){
                endPage ++;
            }
        }
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("currentPage", index);
        model.addAttribute("endPage", endPage);
        return "User/service";
    }

}
