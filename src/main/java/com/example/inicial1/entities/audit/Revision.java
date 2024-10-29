package com.example.inicial1.entities.audit;


import jakarta.persistence.*;
import lombok.*;

import com.example.inicial1.config.CustomRevisionListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;


import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name ="REVISION_INFO")
@RevisionEntity(CustomRevisionListener.class)

@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Revision implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_seq")
    @SequenceGenerator(name = "revision_seq", sequenceName = "revision_sequence", allocationSize = 1)

    @RevisionNumber
    private int id;

    @Column(name = "REVISION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @RevisionTimestamp
    private Date date;  //fecha de la revision

}
