package com.example.inicial1.services;


import com.example.inicial1.dtos.DnaContadorDTO;
import com.example.inicial1.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Slf4j  //permite usar logging
@RequiredArgsConstructor
@Service
public class ContadorService {
    private final PersonRepository personRepository;

    public DnaContadorDTO getStats(){
        log.info("Start ContadorService.getStats()");
        return Stream.of(DnaContadorDTO.builder().build())
                .map(dnaContadorDTO -> dnaContadorDTO.toBuilder()
                        .humanosTotal((int)personRepository.countByIsMutante(Boolean.FALSE))
                        .mutantesTotal((int)personRepository.countByIsMutante(Boolean.TRUE))
                        .build()
                )
                .map(dnaContadorDTO-> dnaContadorDTO.toBuilder()
                        .ratio(calculateRatio(dnaContadorDTO))
                        .build()
                )
                .findAny()
                .orElse(DnaContadorDTO.builder().build());
    }
    private float calculateRatio(DnaContadorDTO dnacountDTO) {
        log.info("calculo de ratio of {}", dnacountDTO);
        return dnacountDTO.getHumanosTotal() == 0
                ? dnacountDTO.getMutantesTotal()
                : (float) dnacountDTO.getMutantesTotal() / dnacountDTO.getHumanosTotal();
    }
}
