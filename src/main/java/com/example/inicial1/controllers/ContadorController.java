package com.example.inicial1.controllers;


import com.example.inicial1.dtos.DnaContadorDTO;
import com.example.inicial1.services.ContadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stats")
public class ContadorController {

    private final ContadorService contadorService;

    @GetMapping()
    public ResponseEntity<DnaContadorDTO> getStats(){
        DnaContadorDTO dnacontadordto =contadorService.getStats();
        return ResponseEntity.ok(dnacontadordto);
    }





}
