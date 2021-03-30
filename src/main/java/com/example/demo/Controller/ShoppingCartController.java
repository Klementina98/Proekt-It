package com.example.demo.Controller;

import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Model.Item;
import com.example.demo.Model.MyUser;
import com.example.demo.Model.ShoppingCart;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ShoppingCartService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserRepository userRepository;
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserRepository userRepository) {
        this.shoppingCartService = shoppingCartService;

        this.userRepository = userRepository;
    }
    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error, HttpServletRequest reg, Model model){

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String username = reg.getRemoteUser();
        if (username ==null || username.isEmpty()){
            return "redirect:/Login";
        }
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("items",this.shoppingCartService.listAllItemsInShoppingCart(shoppingCart.getId()));
        model.addAttribute("totalPrice", this.shoppingCartService.listAllItemsInShoppingCart(shoppingCart.getId()).stream().mapToDouble(Item::getPrice).sum());
        return "Shopping-cart";
    }

    @PostMapping("/add-product/{id}")
    public String addItemToShoppingCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
            String username = req.getRemoteUser();
            MyUser user = userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
            this.shoppingCartService.addProductToShoppingCart(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception) {
            System.out.println("DA IMA");
            return "redirect:/Login";
            //return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @GetMapping("/remove/{id}")
    public String removeItemFromShoppingCart(@PathVariable Long id, HttpServletRequest req){
        System.out.println("Doagja li tuka");
        String username = req.getRemoteUser();
        MyUser user = userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        this.shoppingCartService.removeProductFromShoppingCart(username,id);
        return "redirect:/shopping-cart";
    }
}
