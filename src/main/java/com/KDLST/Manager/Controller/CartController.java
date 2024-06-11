package com.KDLST.Manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

import com.KDLST.Manager.Model.Entity.CartItem.CartItem;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.CartItemService.CartItemService;
import com.KDLST.Manager.Model.Service.CartItemService.CartItemServiceImplement;
import com.KDLST.Manager.Model.Service.CartItemService.CartService;
import com.KDLST.Manager.Model.Service.CartItemService.CartServiceImplement;
import com.KDLST.Manager.Model.Service.CartItemService.VNPayService;
import com.KDLST.Manager.Model.Service.TicketService.TicketService;
import com.KDLST.Manager.Model.Service.TicketService.TicketServiceImplement;

import java.util.ArrayList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartItemService cartItemService = new CartItemServiceImplement();
    CartService cartService = new CartServiceImplement();
    TicketService ticketService = new TicketServiceImplement();
    VNPayService vnPayService = new VNPayService();

    @GetMapping("/allItem")
    public String getAll(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<CartItem> itemList = cartItemService
                .getByIdCart(cartService.getByIdUser(user.getIdUser()).getCartID());
        model.addAttribute("cartItemList", itemList);
        return "User/Cart";
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        cartItemService.delete(Integer.parseInt(id));
        System.out.println("quek");
        return ResponseEntity.ok().body("Xoá thành công");
    }

    @PostMapping(value = "/update")
    public ResponseEntity<String> update(@RequestParam("id") String id, @RequestParam("quantity") String quantity) {
        CartItem cartItem = cartItemService.getById(Integer.parseInt(id));
        int newQuantity = Integer.parseInt(quantity);
        cartItem.setQuantity(newQuantity);
        BigDecimal price = BigDecimal.valueOf(newQuantity)
                .multiply(BigDecimal.valueOf(cartItem.getTicketID().getPrice()));
        cartItem.setPrice(price);
        cartItemService.update(cartItem);
        return ResponseEntity.ok().body("Cập nhật thành công");
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> add(@RequestParam("id") String id, HttpServletRequest request,
            @RequestParam("quantity") String quantity) {
        ArrayList<CartItem> cartItemAll = cartItemService.getAll();
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");
        int idTicket = Integer.parseInt(id);
        if (cartItemAll != null) {
            for (CartItem cartItem : cartItemAll) {
                if (cartItem.getCart().getUser().getIdUser() == userSession.getIdUser()
                        && cartItem.getTicketID().getTicketID() == idTicket) {
                    cartItem.setQuantity(cartItem.getQuantity() + Integer.parseInt(quantity));
                    cartItemService.update(cartItem);
                    return ResponseEntity.ok().body("Thêm thành công");
                }
            }
        }
        CartItem cartItem1 = new CartItem(0, cartService.getByIdUser(userSession.getIdUser()),
                ticketService.getByID(idTicket), Integer.parseInt(quantity),
                BigDecimal.valueOf(Integer.parseInt(quantity))
                        .multiply(BigDecimal.valueOf((ticketService.getByID(idTicket).getPrice()))));
        cartItemAll.add(cartItem1);
        cartItemService.add(cartItem1);
        return ResponseEntity.ok().body("Thêm thành công");
    }

    @PostMapping("/checkOut")
    public String checkOut(@RequestParam(name = "total-price") int price, @RequestParam(name = "info") String info,
            HttpServletRequest request) {

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(price, info, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }

}
