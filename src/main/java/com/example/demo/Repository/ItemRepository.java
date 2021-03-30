package com.example.demo.Repository;

import com.example.demo.Model.Category;
import com.example.demo.Model.Item;
import com.example.demo.Model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategoryAndMaterial(Category category, Material material);
    List<Item> findAllByCategory(Category category);
    List<Item> findAllByMaterial(Material material);


}
