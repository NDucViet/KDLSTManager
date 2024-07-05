package com.KDLST.Manager.Controller;

import java.text.SimpleDateFormat;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;

import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Entity.Bill.BillDetails;
import com.KDLST.Manager.Model.Entity.CartItem.CartItem;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsService;
import com.KDLST.Manager.Model.Service.BillService.BillDetailsServiceImplement;
import com.KDLST.Manager.Model.Service.BillService.BillService;
import com.KDLST.Manager.Model.Service.BillService.BillServiceImplement;
import com.KDLST.Manager.Model.Service.CartItemService.CartItemService;
import com.KDLST.Manager.Model.Service.CartItemService.CartItemServiceImplement;
import com.KDLST.Manager.Model.Service.CartItemService.CartService;
import com.KDLST.Manager.Model.Service.CartItemService.CartServiceImplement;
import com.KDLST.Manager.Model.Service.CartItemService.VNPayService;
import com.KDLST.Manager.Model.Service.TicketService.TicketService;
import com.KDLST.Manager.Model.Service.TicketService.TicketServiceImplement;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.*;
import java.sql.Date;
import java.io.OutputStream;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;

@Controller
@RequestMapping("/cart")
public class CartController {
    private Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    CartItemService cartItemService = new CartItemServiceImplement();
    CartService cartService = new CartServiceImplement();
    TicketService ticketService = new TicketServiceImplement();
    VNPayService vnPayService = new VNPayService();
    BillService billService = new BillServiceImplement();
    BillDetailsService billDetailsService = new BillDetailsServiceImplement();

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
    public String checkOut(@RequestParam(name = "info") String info, @RequestParam(name = "date") String date,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<CartItem> itemList = cartItemService
                .getByIdCart(cartService.getByIdUser(user.getIdUser()).getCartID());
        int price = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Lấy thời gian hiện tại
        java.util.Date utilDate = new java.util.Date();

        // Chuyển đổi thời gian hiện tại sang chuỗi theo định dạng đã chỉ định
        String formattedDate = dateFormat.format(utilDate);
        info += "|" + date + "|" + formattedDate;

        for (CartItem cartItem : itemList) {
            price += cartItem.getPrice().intValue();
        }
        price += price * 0.1;
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        String vnpayUrl = vnPayService.createOrder(request, price, info, baseUrl + "/cart/vnpay-payment-return");
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        HttpSession session = request.getSession();
        String dateUse = orderInfo.split("\\|")[1];
        String datePay = orderInfo.split("\\|")[2];
        User user = (User) session.getAttribute("user");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlPayDate = new Date(0);
        Date sqlUseDate = new Date(0);
        java.util.Date utilDate;
        try {
            utilDate = dateFormat.parse(datePay);
            sqlPayDate = new Date(utilDate.getTime());
            utilDate = dateFormat.parse(dateUse);
            sqlUseDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println(e);
        }
        Bill bill = new Bill(0, user, sqlPayDate, sqlUseDate, 0);
        ArrayList<CartItem> itemList = cartItemService
                .getByIdCart(cartService.getByIdUser(user.getIdUser()).getCartID());
        if (billService.add(bill)) {
            ArrayList<Bill> billList = billService.getByIdUser(user.getIdUser());
            bill = billList.get(billList.size() - 1);

            for (CartItem cartItem : itemList) {
                BillDetails billDetails = new BillDetails(0, bill, cartItem.getTicketID(), cartItem.getQuantity(),
                        cartItem.getPrice().intValue(), 0);
                billDetailsService.add(billDetails);
            }
            cartItemService.delete(cartService.getByIdUser(user.getIdUser()).getCartID());
        }
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1) {
            return "User/ordersuccess";
        } else {
            return "User/orderfail";
        }
    }

    @GetMapping("/history")
    public String getHistory(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        String qrContent = "";
        Map<ArrayList<BillDetails>, Date> billArrayList = new LinkedHashMap<>();
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        ArrayList<Bill> billListt = billService.getByIdUser(user.getIdUser());

        for (Bill bill2 : billListt) {
            ArrayList<BillDetails> bArrayList = billDetailsService.getByBillID(bill2.getBillID());
            for (BillDetails billdt : bArrayList) {
                qrContent += billdt.getBillDetailsID() + " ";
            }
            qrContent += "|" + bill2.getStatus();
            byte[] qrCode = generateQRCode(qrContent, 50, 50);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(qrCode);
            // "/generateQRCode?qrContent=" + qrContent
            billArrayList.put(bArrayList, bill2.getDatePay());
        }

        model.addAttribute("history", billArrayList);
        return "User/history";
    }

    public byte[] generateQRCode(String qrContent, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (WriterException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
