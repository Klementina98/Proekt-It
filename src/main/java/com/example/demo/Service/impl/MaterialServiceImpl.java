package com.example.demo.Service.impl;

import com.example.demo.Exceptions.MaterialNotFoundException;
import com.example.demo.Model.Item;
import com.example.demo.Model.Material;
import com.example.demo.Repository.MaterialRepository;
import com.example.demo.Service.ItemService;
import com.example.demo.Service.MaterialService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final ItemService itemService;

    public MaterialServiceImpl(MaterialRepository materialRepository, ItemService itemService) {
        this.materialRepository = materialRepository;
        this.itemService = itemService;
    }

    @Override
    public List<Material> listAllMaterials() {
        return this.materialRepository.findAll();
    }

    @Override
    public Material findById(Long id) {
        return this.materialRepository.findById(id).orElseThrow(MaterialNotFoundException::new);
    }

    @Override
    public Material save(String typeOfMaterial) {
        return this.materialRepository.save(new Material(typeOfMaterial));
    }

    @Override
    public List<Item> listAllItemsByMaterialId(Long materialId) {
        Material withMat = findById(materialId);
        List<Item> allItems = itemService.listAllItems();
        return allItems.stream().filter(i->i.getMaterial().equals(withMat)).collect(Collectors.toList());
    }
    @Override
    public Material updateMaterial(Long materialId, String type) {
        Material matToUpdate = findById(materialId);
        matToUpdate.setTypeOfMaterial(type);
        materialRepository.save(matToUpdate);
        return matToUpdate;
    }

    @Override
    public void deleteMaterialById(Long materialId) {
        Material matToDelete = findById(materialId);
        this.materialRepository.delete(matToDelete);
    }


}
