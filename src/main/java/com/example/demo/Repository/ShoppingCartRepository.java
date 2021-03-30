package com.example.demo.Repository;

import com.example.demo.Model.MyUser;
import com.example.demo.Model.ShoppingCart;
import com.example.demo.Model.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    Optional<ShoppingCart> findByUserAndStatus(MyUser user, ShoppingCartStatus status);
}
