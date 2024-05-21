package com.KDLST.Manager.Model.Service.TicketService;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Entity.Ticket.TicketType;
@Service
public interface TicketTypeService {
    public ArrayList<TicketType> getAll();
    public TicketType getByID(int id);
    public boolean update(TicketType ticketType);
    public boolean add(TicketType ticketType);
}
