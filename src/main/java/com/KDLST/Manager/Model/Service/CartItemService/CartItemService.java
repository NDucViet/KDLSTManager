package com.KDLST.Manager.Model.Service.CartItemService;

import java.util.ArrayList;
import com.KDLST.Manager.Model.Entity.CartItem.CartItem;

public interface CartItemService {
public ArrayList<CartItem> getAll();

    public CartItem getById(int id);

    public boolean update(CartItem cartItem);

    public boolean add(CartItem cartItem);
    

}
