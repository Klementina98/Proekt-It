package com.example.demo.Service.impl;

import com.example.demo.Exceptions.CategoryNotFoundException;
import com.example.demo.Exceptions.ItemNotFoundException;
import com.example.demo.Exceptions.MaterialNotFoundException;
import com.example.demo.Model.Category;
import com.example.demo.Model.Item;
import com.example.demo.Model.Material;
import com.example.demo.Model.Size;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.MaterialRepository;
import com.example.demo.Service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    public final ItemRepository itemRepository;
    public final CategoryRepository categoryRepository;
    public final MaterialRepository materialRepository;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, MaterialRepository materialRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    public List<Item> listAllItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public Item findById(Long id) {
        return this.itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public Item save(String name, String description, Double price, Long materialId, Long categoryId, Size size, String image1, String image2) throws IOException  {
        Material material = materialRepository.findById(materialId).orElseThrow(ItemNotFoundException::new);
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return this.itemRepository.save(new Item(name,description,price,material,category,size,image1,image2));
    }

    @Override
    public Item edit(Long id, String name, String description,Double price, Long materialId, Long categoryId, Size size, String image1, String image2) {
        Material material = materialRepository.findById(materialId).orElseThrow(ItemNotFoundException::new);
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        Item itemToUpdate = findById(id);
        itemToUpdate.setName(name);
        itemToUpdate.setDescription(description);
        itemToUpdate.setPrice(price);
        itemToUpdate.setMaterial(material);
        itemToUpdate.setCategory(category);
        itemToUpdate.setSize(size);
        itemToUpdate.setImage1(image1);
        itemToUpdate.setImage2(image2);
        itemRepository.save(itemToUpdate);
        return itemToUpdate;
    }

    @Override
    public void deleteItemById(Long id) {
        Item itemToDelete = findById(id);
        this.itemRepository.delete(itemToDelete);
    }

    @Override
    public List<Item> listItemsByMaterialIdAndCategoryId(Long materialId, Long categoryId) {
        List<Item> searchItems=new ArrayList<>();
        
        if (materialId !=null && categoryId!=null){
            Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            Material material = materialRepository.findById(materialId).orElseThrow(MaterialNotFoundException::new);
            searchItems = itemRepository.findAllByCategoryAndMaterial(category,material);
        }else if (materialId == null){
            Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            searchItems = itemRepository.findAllByCategory(category);
        }else if(categoryId == null){
            Material material = materialRepository.findById(materialId).orElseThrow(MaterialNotFoundException::new);
            searchItems = itemRepository.findAllByMaterial(material);
        }else{
            //i dvete se null
            searchItems= itemRepository.findAll();
        }
        return searchItems;
    }


}
