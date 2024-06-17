package com.KDLST.Manager.Model.Repository.CartItemRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.CartItem.Cart;
import com.KDLST.Manager.Model.Entity.CartItem.CartItem;
import com.KDLST.Manager.Model.Entity.Ticket.Ticket;
import com.KDLST.Manager.Model.Repository.TicketRepository.TicketRepository;

import jakarta.el.ELException;

@Repository
public class CartItemRepository {
    ArrayList<CartItem> cartItemList = new ArrayList<>();
    @Autowired
    private CartRepository cartRepository = new CartRepository();
    TicketRepository ticketRepository = new TicketRepository();

    public ArrayList<CartItem> getAll() {
        try {
            cartItemList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.cartitem");
            while (rs.next()) {
                int cartItemID = rs.getInt("cartItemID");
                Cart cart = cartRepository.getById(rs.getInt("cartID"));
                Ticket ticketID = ticketRepository.getById(rs.getInt("ticketId"));
                int quantity = rs.getInt("quantity");
                BigDecimal price = rs.getBigDecimal("price");
                CartItem cartItem = new CartItem(cartItemID, cart, ticketID, quantity, price);
                cartItemList.add(cartItem);
            }
            con.close();
        } catch (Exception e) {

        }
        return cartItemList;
    }

    public ArrayList<CartItem> getByIdCart(int id) {
        ArrayList<CartItem> itemList = new ArrayList<>();
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.cartitem where KDLST.cartitem.cartID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int cartItemID = rs.getInt("cartItemID");
                Cart cart = cartRepository.getById(rs.getInt("cartID"));
                Ticket ticketID = ticketRepository.getById(rs.getInt("ticketID"));
                int quantity = rs.getInt("quantity");
                BigDecimal price = rs.getBigDecimal("price");
                CartItem cartItem = new CartItem(cartItemID, cart, ticketID, quantity, price);
                itemList.add(cartItem);
            }

            conn.close();
            return itemList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public CartItem getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.cartitem where cartItemID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            Cart cart = cartRepository.getById(rs.getInt("cartID"));
            Ticket ticketID = ticketRepository.getById(rs.getInt("ticketID"));
            int quantity = rs.getInt("quantity");
            BigDecimal price = rs.getBigDecimal("price");
            CartItem cartItem = new CartItem(id, cart, ticketID, quantity, price);
            conn.close();
            return cartItem;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean delete(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement("Delete from KDLST.cartitem where cartID = ?;");
            prsm.setInt(1, id);
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public boolean update(CartItem cartItem) {
        try {
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "UPDATE KDLST.cartitem SET CartID = ?,ticketID = ?, quantity = ? ,price = ? WHERE cartItemID = ?");
            prsm.setInt(1, cartItem.getCart().getCartID());
            prsm.setInt(2, cartItem.getTicketID().getTicketID());
            prsm.setInt(3, cartItem.getQuantity());
            prsm.setBigDecimal(4, cartItem.getPrice());
            prsm.setInt(5, cartItem.getCartItemID());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(CartItem cartItem) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "INSERT INTO KDLST.cartitem (CartID,ticketID,Quantity,Price) VALUES ( ?,?,?,?)");
            prsm.setInt(1, cartItem.getCart().getCartID());
            prsm.setInt(2, cartItem.getTicketID().getTicketID());
            prsm.setInt(3, cartItem.getQuantity());
            prsm.setBigDecimal(4, cartItem.getPrice());

            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
