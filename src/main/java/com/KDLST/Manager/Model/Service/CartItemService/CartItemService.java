package com.KDLST.Manager.Model.Service.CartItemService;

import java.util.ArrayList;
import com.KDLST.Manager.Model.Entity.CartItem.CartItem;

public interface CartItemService {
    public ArrayList<CartItem> getAll();

    public ArrayList<CartItem> getByIdCart(int id);

    public CartItem getById(int id);

    public boolean update(CartItem cartItem);

    public boolean add(CartItem cartItem);

    public boolean delete(int id);

    public boolean deleteCartItem(int id) ;

}
