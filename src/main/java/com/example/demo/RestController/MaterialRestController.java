package com.example.demo.RestController;

import com.example.demo.Model.Material;
import com.example.demo.Service.MaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@CrossOrigin(origins = "*")
public class MaterialRestController {

    public final MaterialService materialService;

    public MaterialRestController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping()
    public List<Material> findAll(){
        return this.materialService.listAllMaterials();
    }
    @GetMapping("/{id}")
    public Material findById(@PathVariable Long id){
        return this.materialService.findById(id);
    }
    @PostMapping
    public Material create(String typeOfMaterial){
        return this.materialService.save(typeOfMaterial);
    }
    @PutMapping("/{id}")
    public Material update(@PathVariable Long id, String typeOfMaterial){
        return this.materialService.updateMaterial(id,typeOfMaterial);
    }
    @PostMapping("/delete/{id}")
    public void deleteMaterial(@PathVariable Long id){
        this.materialService.deleteMaterialById(id);
    }

}

