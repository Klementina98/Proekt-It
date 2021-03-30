package com.example.demo.Controller;

import com.example.demo.Model.Category;
import com.example.demo.Model.Item;
import com.example.demo.Model.Material;
import com.example.demo.Model.Size;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ItemService;
import com.example.demo.Service.MaterialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class ItemController {

    private final ItemService itemService;
    public final MaterialService materialService;
    public final CategoryService categoryService;

    public ItemController(ItemService itemService, MaterialService materialService, CategoryService categoryService) {
        this.itemService = itemService;
        this.materialService = materialService;
        this.categoryService = categoryService;
    }

    @GetMapping("/items")
    public String showItems(Model model){
        List<Item> items = itemService.listAllItems();
        model.addAttribute("items",items);
        return "copyHtml";
    }
    @GetMapping("/items/add-form")
    public String getaAddPage(Model model){
        List<Material> materials = this.materialService.listAllMaterials();
        List<Category> categories = this.categoryService.listAllCategories();
        List<Size> sizes = Arrays.asList(Size.values());
        model.addAttribute("categories", categories);
        model.addAttribute("materials",materials);
        model.addAttribute("sizes",sizes);
        return "Add-item";
    }
    @GetMapping("/items/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model){
        Item itemForUbdate = itemService.findById(id);
        List<Material> materials = this.materialService.listAllMaterials();
        List<Category> categories = this.categoryService.listAllCategories();
        List<Size> sizes = Arrays.asList(Size.values());
        model.addAttribute("item",itemForUbdate);
        model.addAttribute("categories", categories);
        model.addAttribute("materials",materials);
        model.addAttribute("sizes",sizes);
        return "Add-item";

    }
    @GetMapping("/items/detailed/{id}")
    public String getDetailedView(@PathVariable Long id, Model model){
        Item itemToView = itemService.findById(id);
        model.addAttribute("item",itemToView);
        return "DetailedView";
    }

    @PostMapping("/items") // /items
    public String create(@RequestParam String name, @RequestParam String description, @RequestParam Double price,
                         @RequestParam Long materialId, @RequestParam Long categoryId, @RequestParam Size size,
                         @RequestParam String image1, @RequestParam String image2) throws IOException {


        Material material = materialService.findById(materialId);
        Category category = categoryService.findCategoryById(categoryId);
        System.out.println(size);
        this.itemService.save(name,description,price,materialId,categoryId,size,image1,image2);

        return "redirect:/items";
    }
    @PostMapping("/items/{id}") // /items/{id}
    public String update(@PathVariable Long id, @RequestParam String name, @RequestParam String description,
                         @RequestParam Double price, @RequestParam Long materialId, @RequestParam Long categoryId,
                         @RequestParam Size size, @RequestParam String image1, @RequestParam String image2) {
        System.out.println("Dali vleguva ovde");

        this.itemService.edit(id,name,description,price,materialId,categoryId,size,image1,image2);
        return "redirect:/items";
    }


    @PostMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable Long id){
        this.itemService.deleteItemById(id);
        return "redirect:/items";
    }




}
