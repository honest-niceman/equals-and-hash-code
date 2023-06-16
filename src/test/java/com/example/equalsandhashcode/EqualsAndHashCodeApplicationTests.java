package com.example.equalsandhashcode;

import com.example.equalsandhashcode.entities.Cat;
import com.example.equalsandhashcode.entities.Dog;
import com.example.equalsandhashcode.repositories.CatRepository;
import com.example.equalsandhashcode.repositories.DogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class EqualsAndHashCodeApplicationTests {

    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private CatRepository catRepository;

    @Test
    void contextLoads() {
    }

    @Sql(scripts = "differentEntitiesTest/insert-dog-and-cat.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "differentEntitiesTest/delete-dog-and-cat.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void differentEntitiesTest() {
        Dog dog = dogRepository.findById(1L).orElseThrow();
        Cat cat = catRepository.findById(1L).orElseThrow();
        Assertions.assertNotEquals(cat, dog);
    }

    @Test
    @Sql(scripts = "addToHashBasedCollectionCausesInitializationTest/insert-dog.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "addToHashBasedCollectionCausesInitializationTest/delete-dog.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addToHashBasedCollectionCausesInitializationTest() {
        Dog proxyDog = dogRepository.getReferenceById(1L);
        Set<Dog> dogs = new HashSet<>();
        dogs.add(proxyDog);
    }
}
