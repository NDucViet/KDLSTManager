package com.KDLST.Manager.Model.Entity.Hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {
    private int roomTypeID;
    private String roomTypeName;
    private double price;
    private String images;
    private String details;
    private int maxPeople;
    private int quantity;
}
