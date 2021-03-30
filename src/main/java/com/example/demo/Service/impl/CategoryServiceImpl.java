package com.example.demo.Service.impl;

import com.example.demo.Exceptions.CategoryNotFoundException;
import com.example.demo.Model.Category;
import com.example.demo.Model.Item;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ItemService itemService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ItemService itemService) {
        this.categoryRepository = categoryRepository;

        this.itemService = itemService;
    }

    @Override
    public List<Category> listAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public Category save(String type) {
        return this.categoryRepository.save(new Category(type));
    }

    @Override
    public List<Item> listItemsByCategoryId(Long categoryId) {
        Category cat = findCategoryById(categoryId);
        List<Item> allItems = itemService.listAllItems();
        return allItems.stream().filter(i->i.getCategory().equals(cat)).collect(Collectors.toList());
    }

    @Override
    public Category edit(Long id, String type) {
        Category catToEdit = findCategoryById(id);
        catToEdit.setType(type);
        categoryRepository.save(catToEdit);
        return catToEdit;
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category catToDelete = findCategoryById(id);
        this.categoryRepository.delete(catToDelete);
    }
}
