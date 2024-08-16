package com.darja.PetManagement.service;

import com.darja.PetManagement.dao.PetDao;
import com.darja.PetManagement.dao.UserDao;
import com.darja.PetManagement.model.Pet;
import com.darja.PetManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetDao petDao;

    @Autowired
    UserDao userDao;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userDao.findByUsername(username);
    }

    public ResponseEntity<List<Pet>> getAllPets() {
        User currentUser = getCurrentUser();
        List<Pet> pets = petDao.findByUserId(currentUser.getId());
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
            User currentUser = getCurrentUser();
            pet.setUser(currentUser);
            Pet savedPet = petDao.save(pet);
            return new ResponseEntity<>(savedPet, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Pet> getPet(Integer id) {
        User currentUser = getCurrentUser();
        Optional<Pet> pet = petDao.findByIdAndUserId(id, currentUser.getId());
        if (pet.isPresent()) {
            return new ResponseEntity<>(pet.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deletePet(Integer id) {
        User currentUser = getCurrentUser();
        Optional<Pet> pet = petDao.findByIdAndUserId(id, currentUser.getId());
        if (pet.isPresent()) {
            petDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
