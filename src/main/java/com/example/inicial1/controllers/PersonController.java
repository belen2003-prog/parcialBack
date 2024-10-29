package com.example.inicial1.controllers;

import com.example.inicial1.dtos.PersonDto;
import com.example.inicial1.entities.Person;
import com.example.inicial1.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/person")
public class PersonController  {

    @Autowired
    private PersonServiceImpl servicio; //modificarlo
    @PostMapping("/mutant")
    public ResponseEntity<String> isMutant(@RequestBody PersonDto persondto) {
        try {
            String[] dna=persondto.getDna(); //saco el array del dto
            boolean isMutant = servicio.isMutant(dna);
            Person person = new Person();
            person.setDna(String.join(",", dna)); // Guardar DNA como una cadena
            person.setMutante(isMutant); // Establecer si es mutante

            // Persistir en la base de datos
            servicio.savePerson(person);
            if (isMutant == true) {
                return ResponseEntity.ok("es mutante"); // Devuelve 200 - OK

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no es mutante"); // Devuelve 403 - Forbidden
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente más tarde\"}");
        }
    }
}
