package com.example.PlanetLords.models;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    /*@ManyToOne(optional=true, cascade=CascadeType.ALL)
    @JoinColumn (name="lord_id")
    PlanetLord planetLord;
*/
}
