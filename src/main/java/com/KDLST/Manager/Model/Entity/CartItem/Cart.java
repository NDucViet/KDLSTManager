package com.KDLST.Manager.Model.Entity.CartItem;


import com.KDLST.Manager.Model.Entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
private int cartID;
private User user;
}

