package com.KDLST.Manager.Model.Service.CartItemService;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Entity.CartItem.CartItem;
import com.KDLST.Manager.Model.Repository.CartItemRepository.CartItemRepository;

@Service
public class CartItemServiceImplement implements CartItemService {

    ArrayList<CartItem> cartItemList = new ArrayList<>();
    @Autowired
    CartItemRepository cartItemRepository = new CartItemRepository();

    @Override
    public ArrayList<CartItem> getAll() {
        this.cartItemList = cartItemRepository.getAll();
        return cartItemList;
    }

    @Override
    public ArrayList<CartItem> getByIdCart(int id) {
        return cartItemRepository.getByIdCart(id);
    }

    @Override
    public boolean update(CartItem cartItem) {
        if (cartItemRepository.update(cartItem)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(CartItem cartItem) {
        if (cartItemRepository.add(cartItem)) {
            return true;
        }
        return false;
    }

    @Override
    public CartItem getById(int id) {
        return cartItemRepository.getById(id);
    }

    @Override
    public boolean delete(int id) {
        if (cartItemRepository.delete(id)) {
            return true;
        }
        return false;
    }

}
