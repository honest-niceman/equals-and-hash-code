package com.example.equalsandhashcode.repositories;

import com.example.equalsandhashcode.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {
}