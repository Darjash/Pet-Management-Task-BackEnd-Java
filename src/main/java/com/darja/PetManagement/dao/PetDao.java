package com.darja.PetManagement.dao;

import com.darja.PetManagement.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetDao extends JpaRepository<Pet, Integer> {
    List<Pet> findByUserId(Integer userId);

    Optional<Pet> findByIdAndUserId(Integer id, Integer id1);
}
