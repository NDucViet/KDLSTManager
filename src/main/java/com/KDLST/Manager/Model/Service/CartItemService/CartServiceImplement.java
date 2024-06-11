package com.KDLST.Manager.Model.Service.CartItemService;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Entity.CartItem.Cart;
import com.KDLST.Manager.Model.Repository.CartItemRepository.CartRepository;

@Service
public class CartServiceImplement implements CartService {
    ArrayList<Cart> cartList = new ArrayList<>();
    @Autowired
    CartRepository cartRepository = new CartRepository();

    @Override
    public ArrayList<Cart> getAll() {
        this.cartList = cartRepository.getAll();
        return cartList;
    }

    @Override
    public Cart getById(int id) {
        return cartRepository.getById(id);
    }

    @Override
    public boolean update(Cart cart) {
        if (cartRepository.update(cart)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Cart cart) {
        if (cartRepository.add(cart)) {
            return true;
        }
        return false;
    }

    @Override
    public Cart getByIdUser(int id) {
        // TODO Auto-generated method stub
        return cartRepository.getByIdUser(id);
    }

}
