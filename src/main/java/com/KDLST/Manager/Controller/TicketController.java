package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Service.TicketService.TicketService;
import com.KDLST.Manager.Model.Service.TicketService.TicketServiceImplement;

import java.util.ArrayList;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    ArrayList<Ticket> tList = new ArrayList<>();

    @Autowired
    TicketService ticketService = new TicketServiceImplement();

    @GetMapping("/getAll")
    public String getAll(Model model) {
        tList = ticketService.getAll();
        return getPage(model, "1");
    }

    @GetMapping("/getAll/{page}")
    public String getPage(Model model, @PathVariable(value = "page") String currentPage) {
        ArrayList<Ticket> ticketList = new ArrayList<>();
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
            ticketList.add(tList.get(i));
        }
        model.addAttribute("ticketList", ticketList);
        model.addAttribute("numPage", numPage);
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        model.addAttribute("Previous", Integer.parseInt(currentPage) - 1);
        model.addAttribute("Next", Integer.parseInt(currentPage) + 1);
        return "User/Ticket";
    }

}
