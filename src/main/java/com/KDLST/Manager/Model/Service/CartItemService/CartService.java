package com.KDLST.Manager.Model.Service.CartItemService;


import java.util.ArrayList;
import com.KDLST.Manager.Model.Entity.CartItem.Cart;

public interface CartService {
public ArrayList<Cart> getAll();

    public Cart getById(int id);

    public boolean update(Cart cart);

    public boolean add(Cart cart);
}
