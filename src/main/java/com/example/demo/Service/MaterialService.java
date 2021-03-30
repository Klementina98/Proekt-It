package com.example.demo.Service;

import com.example.demo.Model.Category;
import com.example.demo.Model.Item;
import com.example.demo.Model.Material;

import java.util.List;

public interface MaterialService {
    List<Material> listAllMaterials();
    Material findById(Long id);
    Material save(String typeOfMaterial);
    List<Item> listAllItemsByMaterialId(Long materialId);
    void deleteMaterialById(Long materialId);
    Material updateMaterial(Long materialId, String type);
}
