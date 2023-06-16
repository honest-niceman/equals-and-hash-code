package com.example.equalsandhashcode.repositories;

import com.example.equalsandhashcode.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}