package com.KDLST.Manager.Model.Entity.ServiceProject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Services {
    private int serviceID;
    private ServiceType serviceTypeID;
    private String description;
    private String image;
    private Date dateTimeEdit;
}
