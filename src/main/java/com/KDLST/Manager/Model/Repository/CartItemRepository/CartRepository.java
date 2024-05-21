package com.KDLST.Manager.Model.Repository.CartItemRepository;


import java.sql.*;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.KDLST.Manager.Model.BaseConnection;
import com.KDLST.Manager.Model.Entity.CartItem.Cart;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;
import jakarta.el.ELException;



@Repository
public class CartRepository {
ArrayList<Cart> cartList = new ArrayList<>();
 @Autowired
 private UserRepository userRepository = new UserRepository();
 
public ArrayList<Cart> getAll() {
        try {
            cartList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from KDLST.cart");
            while (rs.next()) {
                int cartID = rs.getInt("cartID");
               User user = userRepository.getById(rs.getInt("userID"));
               Cart cart= new Cart(cartID, user);
                cartList.add(cart);
            }
            con.close();
        } catch (Exception e) {
            
        }
        return cartList;
    }

    public Cart getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from KDLST.cart where KDLST.cart.cartID = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int cartID = rs.getInt("cartID");
            User user = userRepository.getById(rs.getInt("userID"));
            Cart cart= new Cart(cartID, user);

            st.close();
            return cart;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Cart cart) {
        try (Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username, BaseConnection.password)) {
            PreparedStatement prsm = con.prepareStatement(
                    "UPDATE KDLST.User SET userID = ? WHERE cartID = ?");
            prsm.setInt(1, cart.getUser().getIdUser());
            prsm.setInt(2, cart.getCartID());
    
            int result = prsm.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(Cart cart) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username, BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
            "INSERT INTO KDLST.cart (userID) VALUES ( ?)");
            prsm.setInt(1, cart.getUser().getIdUser());
    
            int result = prsm.executeUpdate();
            con.close();
            return result > 0; 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return false; 
    }
}
