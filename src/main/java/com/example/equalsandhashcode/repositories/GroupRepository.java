package com.example.equalsandhashcode.repositories;

import com.example.equalsandhashcode.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}