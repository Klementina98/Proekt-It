package com.example.demo.Model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne
    private MyUser user;

    @ManyToMany
    private List<Item> items;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    public ShoppingCart() {
    }

    public ShoppingCart(MyUser user) {
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.items = new ArrayList<>();
        this.status = ShoppingCartStatus.CREATED;
    }
}
