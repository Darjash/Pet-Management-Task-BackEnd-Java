package com.darja.PetManagement.dao;

import com.darja.PetManagement.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDao extends JpaRepository<Pet, Integer> {

}
