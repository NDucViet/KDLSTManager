package com.KDLST.Manager.Model.Entity.Ticket;

import com.KDLST.Manager.Model.Entity.ServiceProject.Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private int ticketID;
    private Services service;
    private TicketType ticketTypeID;
    private String title;
    private String description;
    private double price;
    private String image;
    private boolean status;  
}
