package com.example.demo.Service.impl;

import com.example.demo.Exceptions.ItemAlreadyInShoppingCartException;
import com.example.demo.Exceptions.ShoppingCartNotFoundException;
import com.example.demo.Exceptions.TransactionFailedException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Model.*;
import com.example.demo.Repository.ShoppingCartRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ItemService;
import com.example.demo.Service.PaymentService;
import com.example.demo.Service.ShoppingCartService;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final PaymentService paymentService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ItemService itemService, PaymentService paymentService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
        this.paymentService = paymentService;
    }

    @Override
    public List<Item> listAllItemsInShoppingCart(Long cartId) {
        //lets first find active shopp.cart with this cartId
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getItems();

    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        MyUser user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long itemId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Item item = this.itemService.findById(itemId);

        if(shoppingCart.getItems()
                .stream().filter(i -> i.getId().equals(itemId))
                .collect(Collectors.toList()).size() > 0)
            throw new ItemAlreadyInShoppingCartException(itemId, username);
        shoppingCart.getItems().add(item);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeProductFromShoppingCart(String username, Long itemId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        shoppingCart.getItems().removeIf(item -> item.getId().equals(itemId));
        //dali se zachuvuva listata so itemi kako nova

        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) {
        MyUser user = userRepository.findByUsername(userId).orElseThrow(()->new UserNotFoundException(userId));
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserAndStatus(user, ShoppingCartStatus.CREATED).orElseThrow(()->new ShoppingCartNotFoundException(userId));
        List<Item> items = shoppingCart.getItems();
//        float price = 0;
//
//        for (Item item : items) {
//
//            price += item.getPrice();
//        }

        try {
            this.paymentService.pay(chargeRequest);
        } catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException | com.stripe.exception.AuthenticationException e) {
            System.out.println("Vleguva li vo iskluchokot");
            throw new TransactionFailedException(userId);

        }

        shoppingCart.setItems(items);
        shoppingCart.setStatus(ShoppingCartStatus.FINISHED);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
