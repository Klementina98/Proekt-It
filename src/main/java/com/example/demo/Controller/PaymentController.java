package com.example.demo.Controller;

import com.example.demo.Model.ChargeRequest;
import com.example.demo.Model.EmailConf;
import com.example.demo.Model.Item;
import com.example.demo.Model.ShoppingCart;
import com.example.demo.Service.ShoppingCartService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String publicKey;

    private final ShoppingCartService shoppingCartService;
    private final EmailConf emailConf;
    private final UserService userService;

    public PaymentController(ShoppingCartService shoppingCartService, EmailConf emailConf, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.emailConf = emailConf;
        this.userService = userService;
    }


    @GetMapping("/charge")
    public String getCheckoutPage(Model model, HttpServletRequest request) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(request.getRemoteUser());
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency", "ден");
            model.addAttribute("amount", (int) (shoppingCart.getItems().stream().mapToDouble(Item::getPrice).sum() * 100));
            model.addAttribute("stripePublicKey", this.publicKey);
            return "checkout";
        } catch (RuntimeException ex) {
            return "redirect:/items?error=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/charge")
    public String checkout(ChargeRequest chargeRequest,
                           HttpServletRequest request,
                           @RequestParam String name,
                           @RequestParam String lastName,
                           @RequestParam String address,
                           @RequestParam String city,
                           @RequestParam String state,
                           @RequestParam String postcode,
                           @RequestParam String phone,
                           @RequestParam String email) {
        try {
            this.shoppingCartService.checkoutShoppingCart(request.getRemoteUser(), chargeRequest);

            //Create a mail sender
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(this.emailConf.getHost());
            mailSender.setPort(this.emailConf.getPort());
            mailSender.setUsername(this.emailConf.getUsername());
            mailSender.setPassword(this.emailConf.getPassword());

            //Create a email instamce
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("noreply@kupisega.com");
            mailMessage.setTo(email);
            mailMessage.setSubject("Ви благодариме за вашата нарачка!");
            mailMessage.setText("Почитувани "+name + " "+lastName +  "\nНа вашата претходно наведена адреса "
                    +address + " во градот "+city + " општина: "
                    + state + " ќе ви биде испратена вашата нарачка");


            // Send mail
            mailSender.send(mailMessage);
            return "redirect:/items";

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return "redirect:/payments/charge?error=" + ex.getLocalizedMessage();
        }
    }
}