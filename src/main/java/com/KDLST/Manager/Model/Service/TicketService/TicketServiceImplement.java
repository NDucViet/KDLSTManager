package com.KDLST.Manager.Model.Service.TicketService;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Repository.TicketRepository.TicketRepository;

@Service
public class TicketServiceImplement implements TicketService {
    ArrayList<Ticket> ticketList = new ArrayList<>();
    @Autowired
    TicketRepository ticketRepository = new TicketRepository();
    @Override
    public ArrayList<Ticket> getAll(){
        this.ticketList = ticketRepository.getAll();
        return ticketList;
    }
    @Override
    public Ticket getByID(int id){
        return ticketRepository.getById(id);
    }
    @Override
    public boolean update(Ticket ticket){
        if (ticketRepository.update(ticket)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean add(Ticket ticket){
        if (ticketRepository.add(ticket)) {
            return true;
        }
        return false;
    }
}
