package com.example.equalsandhashcode;

import com.example.equalsandhashcode.entities.Cat;
import com.example.equalsandhashcode.entities.Dog;
import com.example.equalsandhashcode.repositories.CatRepository;
import com.example.equalsandhashcode.repositories.DogRepository;
import jakarta.transaction.Transactional;
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

    @Test
    @Sql(scripts = "addTwoProxiesToHashBasedCollectionCausesInitializationTest/insert-dogs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "addTwoProxiesToHashBasedCollectionCausesInitializationTest/delete-dogs.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addTwoProxiesToHashBasedCollectionCausesInitializationTest() {
        Dog proxyDog1 = dogRepository.getReferenceById(1L);
        Dog proxyDog2 = dogRepository.getReferenceById(2L);
        Set<Dog> dogs = new HashSet<>();
        dogs.add(proxyDog1);
        dogs.add(proxyDog2);
    }

    @Sql(scripts = "compareEntityAndProxyTest/insert-dog.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "compareEntityAndProxyTest/delete-dog.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    @Test
    public void compareEntityAndProxyTest() {
        Dog entity = dogRepository.findById(1L).orElseThrow();
        Dog proxy = dogRepository.getReferenceById(1L);
        Assertions.assertEquals(entity, proxy);
    }
}
