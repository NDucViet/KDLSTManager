package com.KDLST.Manager.Model.Service.TicketService;

import java.util.*;
import java.util.stream.*;
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
    public ArrayList<Ticket> getAll() {
        this.ticketList = ticketRepository.getAll();
        return ticketList;
    }

    @Override
    public ArrayList<Ticket> getTicketByTicketTypeId(int id) {
        ArrayList<Ticket> ticketList = ticketRepository.getAll();
        return ticketList;
    }

    @Override
    public Ticket getByID(int id) {
        return ticketRepository.getById(id);
    }

    @Override
    public boolean update(Ticket ticket) {
        if (ticketRepository.update(ticket)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Ticket ticket) {
        if (ticketRepository.add(ticket)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Ticket> getTicketsByTypes(List<String> ticketTypes) {
        List<Ticket> allTickets = ticketRepository.getAll();
        for (Ticket ticket : allTickets) {
            if (ticket.getTicketTypeID() != null) {
                System.out.println("Ticket ID: " + ticket.getTicketID() + " Ticket Type ID: "
                        + ticket.getTicketTypeID().getTicketTypeID());
            } else {
                System.out.println("Ticket ID: " + ticket.getTicketID() + " has null TicketType");
            }
        }
        // Filter tickets
        List<Ticket> filteredTickets = allTickets.stream()
                .filter(ticket -> ticketTypes.contains(ticket.getTicketTypeID().getTicketTypeName()))
                .collect(Collectors.toList());

        // Print the result
        System.out.println("Filtered Tickets: " + filteredTickets.size());
        for (Ticket ticket : filteredTickets) {
            System.out.println("Filtered Ticket ID: " + ticket.getTicketID() + " Ticket Type ID: "
                    + ticket.getTicketTypeID().getTicketTypeID());
        }

        return filteredTickets;
    }

}
