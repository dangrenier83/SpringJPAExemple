package com.julexmile.exemplejpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.julexmile.exemplejpa.data.DocteurRepository;
import com.julexmile.exemplejpa.models.Docteur;

@Controller
public class ModifyDocteurController {

    DocteurRepository repo_doctor;

    @Autowired
    public ModifyDocteurController(DocteurRepository repo_doctor) {
        this.repo_doctor = repo_doctor;
    }

    @RequestMapping(value = "/modifydocteur", method = RequestMethod.POST)
    public String modifierDocteur(@RequestParam String doctorId, @RequestParam String firstName,
                           @RequestParam String lastName, @RequestParam String city, @RequestParam String username,
                           Model model) {

        Docteur d = repo_doctor.findById(Long.valueOf(doctorId)).orElse(null);
        d.setFirstName(firstName);
        d.setLastName(lastName);
        d.setCity(city);
        d.setUsername(username);
        repo_doctor.save(d);

        Iterable<Docteur> docteurs = repo_doctor.findAll();
        model.addAttribute("dl", docteurs);

        return "docteurList";
    }
}