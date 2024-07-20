package com.KDLST.Manager.Model.Service.TicketService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Entity.Ticket.TicketSold;
import com.KDLST.Manager.Model.Repository.TicketRepository.TicketSoldRepository;

@Service
public class TicketSoldImplement implements TicketSoldService {

    ArrayList<TicketSold> ticketSoldList = new ArrayList<>();

    @Autowired
    TicketSoldRepository ticketSoldRepository = new TicketSoldRepository();

    @Override
    public ArrayList<TicketSold> getAllTicketSold() {
        ArrayList<TicketSold> ticketSoldList = ticketSoldRepository.getAllTicketSold();
        if (ticketSoldList != null) {
            return ticketSoldList;
        }
        return null;
    }

    @Override
    public ArrayList<TicketSold> getAll() {
        this.ticketSoldList = ticketSoldRepository.getAll();
        return ticketSoldList;
    }

    public ArrayList<TicketSold> getByUserID(int UserID) {
        ArrayList<TicketSold> ticketSoldList = ticketSoldRepository.getByUserID(UserID);
        if (ticketSoldList != null) {
            return ticketSoldList;
        }
        return null;
    }

    @Override
    public TicketSold getByID(String id) {
        return ticketSoldRepository.getById(id);
    }

    @Override
    public boolean update(TicketSold ticketSold) {
        if (ticketSoldRepository.update(ticketSold)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(TicketSold ticketSold) {
        if (ticketSoldRepository.add(ticketSold)) {
            return true;
        }
        return false;
    }

   
}
