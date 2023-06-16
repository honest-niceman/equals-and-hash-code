package com.example.equalsandhashcode.repositories;

import com.example.equalsandhashcode.entities.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}