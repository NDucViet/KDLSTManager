package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KDLST.Manager.Model.Entity.RateAFb.FeedBack;
import com.KDLST.Manager.Model.Entity.RateAFb.Rate;
import com.KDLST.Manager.Model.Entity.ServiceProject.Services;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackService;
import com.KDLST.Manager.Model.Service.RateAFbService.FeedBackServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.RateService;
import com.KDLST.Manager.Model.Service.RateAFbService.RateServiceImplement;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceService;
import com.KDLST.Manager.Model.Service.ServiceProjectService.ServiceServiceImplement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/service")
public class ServiceController {
    ArrayList<Services> sList = new ArrayList<>();
    @Autowired
    private ServiceService serviceService = new ServiceServiceImplement();
    private FeedBackService feedBackService = new FeedBackServiceImplement();
    private RateService rateService = new RateServiceImplement();

    @GetMapping("/getAll")
    public String getAll(Model model) {
        sList = serviceService.getAll();
        return getPage(model, "1");
    }

    @GetMapping("/getAll/{page}")
    public String getPage(Model model, @PathVariable(value = "page") String currentPage) {
        int servicesPerPage = 6;
        int numPages = (int) Math.ceil((float) sList.size() / servicesPerPage);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }

        HashMap<Map.Entry<Services, ArrayList<FeedBack>>, Float> serviceList = new HashMap<>();
        for (int i = (Integer.parseInt(currentPage) - 1) * servicesPerPage; i < Integer.parseInt(currentPage)
                * servicesPerPage; i++) {
            if (sList.size() <= i)
                break;
            ArrayList<FeedBack> feedBacks = new ArrayList<>();
            for (FeedBack feedBack : feedBackService.getAll()) {
                if (feedBack.getServices().getServiceID() == sList.get(i).getServiceID()) {
                    feedBacks.add(feedBack);
                }
                Collections.reverse(feedBacks);
            }
            Map.Entry<Services, ArrayList<FeedBack>> SFHashMap = new AbstractMap.SimpleEntry<>(sList.get(i), feedBacks);
            serviceList.put(SFHashMap, rateService.getScoreByService(sList.get(i)));
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

    @GetMapping("/serviceSuggestion")
    public ResponseEntity<ArrayList<Services>> roomTypeSuggestion(@RequestParam("keyword") String keyword) {
        ArrayList<Services> services = serviceService.searchService(keyword);
        return ResponseEntity.ok().body(services);
    }

    @PostMapping("/addFeedback")
    public ResponseEntity<FeedBack> addFeedback(@RequestParam("content") String content,
            @RequestParam("service") String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedNow = now.format(formatter);

        FeedBack feedBack = new FeedBack(0, user, serviceService.getById(Integer.parseInt(id)), content,
                Date.valueOf(formattedNow));
        feedBackService.add(feedBack);
        return ResponseEntity.ok().body(feedBack);
    }

    @PostMapping("/deleteFeedback")
    public ResponseEntity<String> deleteFeedback(@RequestParam("idFeedback")String idFeedback) {
        feedBackService.delete(Integer.parseInt(idFeedback));
        return ResponseEntity.ok().body("Xoá thành công");
    }
}
