package com.julexmile.exemplejpa.data;

import org.springframework.data.repository.CrudRepository;

import com.julexmile.exemplejpa.models.Docteur;

public interface DocteurRepository extends CrudRepository<Docteur, Long> {

    Docteur findByUsername(String username);
    
}
