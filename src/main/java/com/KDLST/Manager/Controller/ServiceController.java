package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;

import java.util.ArrayList;

@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceServiceImplement serviceServiceImplement = new ServiceServiceImplement();

    @GetMapping("/getAll")
    public String getAll(Model model, @RequestParam(name = "index", defaultValue = "1") int index,
            @RequestParam(name = "serviceTypeID", defaultValue = "1") int serviceTypeID) {
        ArrayList<Services> sList = serviceServiceImplement.getSerBySerTypeID(serviceTypeID);
        int endPage = 1;
        if (sList != null && !sList.isEmpty()) {
            int count = sList.size();
            endPage = count / 6;
            if (count % 6 != 0) {
                endPage++;
            }
        }

        ArrayList<Services> serviceList = serviceServiceImplement.getPageService(index, serviceTypeID);
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("currentPage", index);
        model.addAttribute("endPage", endPage);
        return "User/service";
    }

}
