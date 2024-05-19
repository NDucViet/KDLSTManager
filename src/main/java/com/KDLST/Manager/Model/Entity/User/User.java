package com.KDLST.Manager.Model.Entity.User;

import java.sql.Blob;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int idUser;
    private CustomerType customerType;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String cardID;
    private String address;
    private Date dob;
    private int gender;
    private Blob avatar;
    private String nation;
    private String role;
    private Boolean status;
}
