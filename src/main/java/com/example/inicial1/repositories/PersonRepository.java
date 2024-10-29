package com.example.inicial1.repositories;

import com.example.inicial1.entities.Person;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long>{
   Optional<Person> findByDna(String dna);
   long countByIsMutante(boolean isMutante);
}
