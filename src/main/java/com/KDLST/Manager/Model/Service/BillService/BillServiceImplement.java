package com.KDLST.Manager.Model.Service.BillService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.KDLST.Manager.Model.Entity.Bill.Bill;
import com.KDLST.Manager.Model.Repository.BillRepository.BillRepository;

@Service
public class BillServiceImplement implements BillService {

    ArrayList<Bill> billList = new ArrayList<>();
    @Autowired
    BillRepository billRepository = new BillRepository();
    private static final Predicate<Date> DATE_PAY_VALIDATOR = datePay -> datePay != null
            && !datePay.toLocalDate().isAfter(LocalDate.now());

    private static final Predicate<Boolean> STATUS_VALIDATOR = status -> status != null;

    @Override
    public ArrayList<Bill> getAll() {
        this.billList = billRepository.getAll();
        return billList;
    }

    @Override
    public Bill getById(int id) {
        return billRepository.getById(id);
    }

    @Override
    public boolean update(Bill bill) {
        if (billRepository.update(bill)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Bill bill) {
        if (billRepository.add(bill)) {
            return true;
        }
        return false;
    }

    public ArrayList<String> getInvalidAttributes(Bill bill) {
        ArrayList<String> invalidAttributes = new ArrayList<>();
        if (!DATE_PAY_VALIDATOR.test(bill.getDatePay())) {
            invalidAttributes.add("datePay");
        }
        if (!STATUS_VALIDATOR.test(bill.isStatus())) {
            invalidAttributes.add("status");
        }
        return invalidAttributes;
    }

    @Override
    public ArrayList<Bill> getByIdUser(int id) {
        return billRepository.getByIdUser(id);
    }

}
