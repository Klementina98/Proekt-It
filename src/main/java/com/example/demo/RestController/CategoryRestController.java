package com.example.demo.RestController;

import com.example.demo.Model.Category;
import com.example.demo.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryRestController {

    public final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll(){
        return categoryService.listAllCategories();
    }
    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id){
        return categoryService.findCategoryById(id);
    }
    @PostMapping("/createCategory")
    public Category save(String type){
        return categoryService.save(type);
    }
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, String type) {
        return this.categoryService.edit(id, type);
    }
    @PostMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        this.categoryService.deleteCategoryById(id);
    }

}
