//package com.example.demo.RestController;
//
//import com.example.demo.Model.Category;
//import com.example.demo.Model.Item;
//import com.example.demo.Model.Material;
//import com.example.demo.Model.Size;
//import com.example.demo.Service.ItemService;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/items")
//@CrossOrigin(origins = "*")
//public class ItemRestController {
//
//    public final ItemService itemService;
//
//    public ItemRestController(ItemService itemService) {
//        this.itemService = itemService;
//    }
//
//    @GetMapping()
//    public List<Item> findAll(){
//        return this.itemService.listAllItems();
//    }
//    @GetMapping("/{id}")
//    public Item findById(@PathVariable Long id){
//        return this.itemService.findById(id);
//    }
//    @GetMapping("/by-materialAndBy-categoryId/{materialId}/{categoryId}")
//    public List<Item> findAllByMaterialId(@PathVariable Long materialId, @PathVariable Long categoryId){
//        return this.itemService.listItemsByMaterialIdAndCategoryId(materialId,categoryId);
//    }
//
//    @PostMapping("/createItem")
//    public Item create(String name, String description,
//                       Material material, Category category, Size size, String image) throws IOException {
//        return this.itemService.save(name,description,material,category,size,image);
//    }
//
//    @PutMapping("/{id}")
//    public Item update(@PathVariable Long id,@RequestParam(required = false) String name,
//                       @RequestParam(required = false) String description,
//                       @RequestParam(required = false) Material material, @RequestParam(required = false) Category category,
//                       @RequestParam(required = false) Size size,
//                       @RequestParam (required = false) String image){
//        return this.itemService.edit(id,name,description,material,category,size,image);
//    }
//    @PostMapping("/delete/{id}")
//    public void delete(@PathVariable Long id){
//        this.itemService.deleteItemById(id);
//    }
//
//}
