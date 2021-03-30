package com.example.demo.Service;

import com.example.demo.Model.Category;
import com.example.demo.Model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> listAllCategories();
    Category findCategoryById(Long id);
    Category save(String type);
    List<Item> listItemsByCategoryId(Long categoryId);
    Category edit(Long id, String type);
    void deleteCategoryById(Long id);
}
