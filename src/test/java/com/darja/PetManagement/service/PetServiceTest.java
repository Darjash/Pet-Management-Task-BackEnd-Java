package com.darja.PetManagement.service;

import com.darja.PetManagement.dao.PetDao;
import com.darja.PetManagement.dao.UserDao;
import com.darja.PetManagement.model.Pet;
import com.darja.PetManagement.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetServiceTest {

    @Mock
    private PetDao petDao;

    @Mock
    private UserDao userDao;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private PetService petService;


    private User user;
    private Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        user = new User();
        user.setId(1);
        when(authentication.getName()).thenReturn("user");
        when(userDao.findByUsername("user")).thenReturn(user);

        pet = new Pet();
        pet.setId(1);
    }


    @Test
    @DisplayName("Should return all pets for the current user")
    void shouldReturnAllPetsForCurrentUser() {
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet());
        when(petDao.findByUserId(1)).thenReturn(pets);

        ResponseEntity<List<Pet>> response = petService.getAllPets();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }
    @Test
    @DisplayName("Should add a pet for the current user")
    void shouldAddPetForCurrentUser() {
        when(petDao.save(pet)).thenReturn(pet);

        ResponseEntity<Pet> response = petService.addPet(pet);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pet, response.getBody());
    }

    @Test
    @DisplayName("Should return Bad Request when adding a null pet")
    void shouldReturnBadRequestWhenAddingNullPet() {
        ResponseEntity<Pet> response = petService.addPet(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return the pet of the current user")
    void shouldGetPetOfCurrentUser(){
        when(petDao.findByIdAndUserId(1,1)).thenReturn(Optional.of(pet));
        ResponseEntity<Pet> response = petService.getPet(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pet, response.getBody());
    }
    @Test
    @DisplayName("Should delete the pet of the current user")
    void shouldDeletePetOfCurrentUser(){
        when(petDao.findByIdAndUserId(1,1)).thenReturn(Optional.of(pet));
        ResponseEntity<Void> response = petService.deletePet(1);

        verify(petDao, times(1)).deleteById(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    @DisplayName("Should return Not Found when deleting a nonexistent pet")
    void shouldReturnNotFoundWhenDeletingNonexistentPet() {
        when(petDao.findByIdAndUserId(1, 1)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = petService.deletePet(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(petDao, times(0)).deleteById(anyInt());
    }

}
