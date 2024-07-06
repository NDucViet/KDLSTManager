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

import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
import com.KDLST.Manager.Model.Entity.RateAFb.Rate;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsService;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.RateAFbService.RateService;
import com.KDLST.Manager.Model.Service.RateAFbService.RateServiceImplement;
import com.KDLST.Manager.Model.Service.TicketService.TicketService;
import com.KDLST.Manager.Model.Service.TicketService.TicketServiceImplement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    ArrayList<Ticket> tList = new ArrayList<>();

    @Autowired
    TicketService ticketService = new TicketServiceImplement();
    RateService rateService = new RateServiceImplement();
    BillDetailsService billDetailsService = new BillDetailsServiceImplement();

    @GetMapping("/getAll")
    public String getAll(Model model) {
        tList = ticketService.getAll();
        return getPage(model, "1");
    }

    @GetMapping("/getAll/{page}")
    public String getPage(Model model, @PathVariable(value = "page") String currentPage) {
        HashMap<Ticket,Float> ticketList = new HashMap<>();
        int ticketPage = 5;
        int numPages = (int) Math.ceil((float) tList.size() / ticketPage);
        int[] numPage = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            numPage[i] = i + 1;
        }
        for (int i = (Integer.parseInt(currentPage) - 1) * ticketPage; i < Integer.parseInt(currentPage)
                * ticketPage; i++) {
            if (tList.size() <= i)
                break;
            ticketList.put(tList.get(i),rateService.getScoreByService(tList.get(i)));
        }
        model.addAttribute("ticketList", ticketList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "User/Ticket";
    }

    @PostMapping(value = "/rating")
    public ResponseEntity<String> ratingBook(@RequestParam("idBillDetail") String idBillDetail,@RequestParam("idTicket") String idTicket,
            @RequestParam("score") String score, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Rate rate = new Rate(0, user, Integer.parseInt(score), ticketService.getByID(Integer.parseInt(idTicket)));
        System.out.println(idBillDetail);
        BillDetails billDetails = billDetailsService.getByID(Integer.parseInt(idBillDetail));
        billDetails.setStatus(2);
        billDetailsService.update(billDetails);
        rateService.add(rate);
        return ResponseEntity.ok().body("Đánh giá thành công");
    }

}
