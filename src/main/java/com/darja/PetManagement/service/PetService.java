package com.darja.PetManagement.service;

import com.darja.PetManagement.dao.PetDao;
import com.darja.PetManagement.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetDao petDao;

    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petDao.findAll();
        if (pets != null && !pets.isEmpty()) {
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<Pet> addPet(Pet pet) {
        if (pet == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Pet savedPet = petDao.save(pet);
            return new ResponseEntity<>(savedPet, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Pet> getPet(Integer id) {
        Optional<Pet> pet = petDao.findById(id);
        if (pet.isPresent()) {
            return new ResponseEntity<>(pet.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deletePet(Integer id) {
        Optional<Pet> pet = petDao.findById(id);
        if (pet.isPresent()) {
            petDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
