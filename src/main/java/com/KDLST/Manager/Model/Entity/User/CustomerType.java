package com.KDLST.Manager.Model.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerType {
    private int idCusType;
    private String nameCusType;
    private String detail;
}
