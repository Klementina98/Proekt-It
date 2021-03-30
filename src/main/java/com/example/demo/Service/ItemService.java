package com.example.demo.Service;


import com.example.demo.Model.Item;
import com.example.demo.Model.Size;


import java.io.IOException;
import java.util.List;

public interface ItemService {
     List<Item> listAllItems();
     Item findById(Long id);
     Item save(String name, String description, Double price,Long materialId, Long categoryId, Size size, String image1, String image2) throws IOException;
     Item edit(Long id,String name,String description, Double price,Long materialId, Long categoryId,Size size, String image1, String image2);
     void deleteItemById(Long id);

     List<Item> listItemsByMaterialIdAndCategoryId(Long materialId, Long categoryId);

}
