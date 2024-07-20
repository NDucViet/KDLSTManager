package com.KDLST.Manager.Model.Service.TicketService;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Entity.Ticket.TicketSold;

@Service
public interface TicketSoldService {
    public ArrayList<TicketSold> getAll();

    public TicketSold getByID(String id);

    public boolean update(TicketSold ticketType);

    public boolean add(TicketSold ticketType);

    public ArrayList<TicketSold> getByUserID(int UserID);

    public ArrayList<TicketSold> getAllTicketSold();

}
