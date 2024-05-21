package com.KDLST.Manager.Model.Entity.Hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private int roomID;
    private Hotel hotel;
    private RoomType roomType;
    private double acreage;
    private int maxPeople;
    private double price;
    private String image;
    private String amenities;
    private boolean status;

}
