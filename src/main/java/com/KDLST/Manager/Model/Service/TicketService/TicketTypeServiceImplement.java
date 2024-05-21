package com.KDLST.Manager.Model.Service.TicketService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KDLST.Manager.Model.Entity.Ticket.TicketType;
import com.KDLST.Manager.Model.Repository.TicketRepository.TicketTypeRepository;

@Service
public class TicketTypeServiceImplement implements TicketTypeService {
    ArrayList<TicketType> ticketTypeList = new ArrayList<>();
    @Autowired
    TicketTypeRepository ticketTypeRepository = new TicketTypeRepository();
    @Override
    public ArrayList<TicketType> getAll(){
        this.ticketTypeList = ticketTypeRepository.getAll();
        return ticketTypeList;
    }
    @Override
    public TicketType getByID(int id){
        return ticketTypeRepository.getById(id);
    }
    @Override
    public boolean update(TicketType ticketType){
        if (ticketTypeRepository.update(ticketType)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean add(TicketType ticketType){
        if (ticketTypeRepository.add(ticketType)) {
            return true;
        }
        return false;
    }
}
