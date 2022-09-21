package com.julexmile.exemplejpa.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Docteur {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_doctor")
    @SequenceGenerator(name = "gen_doctor", sequenceName = "SEQ_DOCTOR", allocationSize = 1)
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String city;

    public Docteur(String username, String firstName, String lastName, String city) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }
    
}