package com.example.demo.Service;

import com.example.demo.Model.ChargeRequest;
import com.example.demo.Model.Item;
import com.example.demo.Model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingCartService {

    List<Item> listAllItemsInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addProductToShoppingCart(String username,Long itemId);
    ShoppingCart removeProductFromShoppingCart(String username, Long itemId);
    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest);
}
