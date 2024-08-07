package com.KDLST.Manager.Model.Service.UserService;

import java.util.ArrayList;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import com.KDLST.Manager.Model.Entity.User.User;
import com.KDLST.Manager.Model.Repository.UserRepository.UserRepository;

import java.util.HashSet;

@Service
public class UserServiceImplement implements UserService {

    // ArrayList<User> userList = new ArrayList<>();
    @Autowired
    UserRepository userRepository = new UserRepository();

    private static final Predicate<String> EMAIL_VALIDATOR = email -> email
            .matches("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");

    private static final Predicate<String> PASSWORD_VALIDATOR = password -> password.length() >= 8;

    private static final Predicate<String> PHONE_VALIDATOR = phone -> phone.matches("^\\d{10}$");

    private static final Predicate<Date> BIRTH_VALIDATOR = birth -> {
        LocalDate birthDate = birth.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return !birthDate.isAfter(currentDate);
    };

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> userList = userRepository.getAll();
        return userList;
    }

    @Override
    public ArrayList<User> getAllCustomer() {
        ArrayList<User> userList = userRepository.getAllCustomer();
        return userList;
    }

    
    @Override
    public ArrayList<User> getAllEmployee() {
        ArrayList<User> userList = userRepository.getAllEmployee();
        return userList;
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public boolean update(User user) {
        if (userRepository.update(user)) {
            return true;
        }
        return false;
    }

    public User login(String email) {
        for (User user2 : userRepository.getAll()) {
            if (user2.getEmail().equals(email)) {
                return user2;
            }
        }
        return null;
    }

    public boolean toLogin(User user) {
        for (User user2 : userRepository.getAll()) {
            if (user2.getEmail().equals(user.getEmail()) && user2.getPassword().equals(user.getPassword()) && user2.getStatus() == true) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> getInvalidAttributes(User user) {
        ArrayList<String> invalidAttributes = new ArrayList<>();
        HashSet<User> setList = new HashSet<>();
        setList.addAll(userRepository.getAll());
        if (!setList.add(user)) {
            invalidAttributes.add("doublicate");
        }
        if (!EMAIL_VALIDATOR.test(user.getEmail())) {
            invalidAttributes.add("email");
        }
        if (!PASSWORD_VALIDATOR.test(user.getPassword())) {
            invalidAttributes.add("password");
        }
        if (!PHONE_VALIDATOR.test(user.getPhoneNumber())) {
            invalidAttributes.add("phone");
        }
        if (!BIRTH_VALIDATOR.test(user.getDob())) {
            invalidAttributes.add("birth");
        }

        return invalidAttributes;
    }

    public boolean banCustomer(User user){
        if(userRepository.banCustomer(user)){
            return true;
        }
        return false;
    }

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vietndde170616@fpt.edu.vn");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Sent successfully....");
    }

    @Override
    public boolean add(User user) {
        if (userRepository.add(user)) {
            return true;
        }
        return false;
    }

}
