package com.darja.PetManagement.controller;

import com.darja.PetManagement.dao.PetDao;
import com.darja.PetManagement.model.Pet;
import com.darja.PetManagement.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4210")
@RestController
public class PetManagementController {

    @Autowired
    PetService petService;

    @GetMapping("pets")
    public ResponseEntity<List<Pet>> getAllPets(){
        return petService.getAllPets();
    }

    @PostMapping("add")
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @GetMapping("getPet/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Integer id) {
        return petService.getPet(id);
    }

    @DeleteMapping ("deletePet/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Integer id) {
        return petService.deletePet(id);
    }
}
