package com.example.inicial1.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class DnaContadorDTO {
    @JsonProperty("contador_mutantes")
    private int mutantesTotal;
    @JsonProperty("contador_humanos")
    private int humanosTotal;
    private float ratio;
}
