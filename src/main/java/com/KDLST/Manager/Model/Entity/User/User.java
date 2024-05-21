package com.KDLST.Manager.Model.Entity.User;

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
    private String avatar;
    private String nation;
    private String role;
    private Boolean status;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User person = (User) obj;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
