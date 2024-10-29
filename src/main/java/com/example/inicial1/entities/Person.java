package com.example.inicial1.entities;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.envers.Audited;

//jpa
@Entity
//
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
//auditoria
@Audited
public class Person extends Base{

    private String dna;
    private boolean isMutante;
}
