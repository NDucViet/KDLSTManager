package com.KDLST.Manager.Model.Entity.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private int cartItemID;
    private Cart cart;
    private int ticketID;
    private int quantity;
    private BigDecimal price;
}
