package com.KDLST.Manager.Model.Entity.Hotel;

import com.KDLST.Manager.Model.Entity.ServiceProject.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    private int hotelID;
    private ServiceType serviceType;
    private String hotelName;
}
