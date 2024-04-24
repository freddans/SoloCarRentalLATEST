package com.example.redogroupsolo.repositories;

import com.example.redogroupsolo.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    boolean existsByRegNr(String regNr);
    Car findByRegNr(String regNr);
}
