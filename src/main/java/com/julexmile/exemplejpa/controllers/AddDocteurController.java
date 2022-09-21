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
public class AddDocteurController {

    private final DocteurRepository repo_docteur;

    @Autowired
    public AddDocteurController(DocteurRepository repo_docteur) {
        this.repo_docteur = repo_docteur;
    }

    @RequestMapping(value = "/ajoutdocteur", method = RequestMethod.GET)
    public String afficheFormulaireAjoutDocteur() {
        return "ajoutdocteur";
    } 

    @RequestMapping(value = "/ajoutdocteur", method = RequestMethod.POST)
    public String ajouteDocteur(@RequestParam String firstName, @RequestParam String lastName,
                                @RequestParam String city, @RequestParam String username, Model model) {
        Docteur d = new Docteur(username, firstName, lastName, city);
        repo_docteur.save(d);

        Iterable<Docteur> docteurs = repo_docteur.findAll();
        model.addAttribute("dl", docteurs);

        return "docteurList";
    }
}
