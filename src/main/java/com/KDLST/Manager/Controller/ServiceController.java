package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;

import java.util.ArrayList;

@Controller
@RequestMapping("/service")
public class ServiceController {
    ArrayList<Services> sList = new ArrayList<>();
    @Autowired
    private ServiceService serviceService = new ServiceServiceImplement();

    @GetMapping("/getAll")
    public String getAll(Model model) {
        sList = serviceService.getAll();
        return getPage(model, "1");
    }

    @GetMapping("/getAll/{page}")
    public String getPage(Model model, @PathVariable(value = "page") String currentPage) {
        ArrayList<Services> serviceList = new ArrayList<>();
        int servicesPerPage = 6;
        int numPages = (int) Math.ceil((float) sList.size() / servicesPerPage);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }
        for (int i = (Integer.parseInt(currentPage) - 1) * servicesPerPage; i < Integer.parseInt(currentPage)
                * servicesPerPage; i++) {
            if (sList.size() <= i)
                break;
            serviceList.add(sList.get(i));
        }
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "User/service";
    }

    @GetMapping("/getByID/{id}")
    public String getByID(@PathVariable(value = "id") int id, Model model) {
        sList = serviceService.getSerBySerTypeID(id);
        return getPage(model, "1");
    }

    @GetMapping("/searchService")
    public String searchRoomType(Model model, @RequestParam("keyword") String keyword) {
        sList = serviceService.searchService(keyword);
        return getPage(model, "1");
    }

    @GetMapping(value = "/serviceSuggestion")
    public ResponseEntity<ArrayList<Services>> roomTypeSuggestion(@RequestParam("keyword") String keyword) {
        ArrayList<Services> services = serviceService.searchService(keyword);
        return ResponseEntity.ok().body(services);
    }

}
