package com.example.demo.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    @ManyToOne
    private Material material;
    @ManyToOne
    private Category category;

    @Enumerated
    private Size size;

    private String image1;
    private String image2;


    public Item() {
    }


    public Item(String name, String description, Double price, Material material, Category category, Size size, String image1, String image2) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.material = material;
        this.category = category;
        this.size = size;
        this.image1 = image1;
        this.image2 = image2;
    }
}
