package com.julexmile.exemplejpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.julexmile.exemplejpa.data.DocteurRepository;
import com.julexmile.exemplejpa.models.Docteur;

@Controller
public class DocteurListController {

    DocteurRepository repo_doctor;

    @Autowired
    public DocteurListController(DocteurRepository repo_doctor) {
        this.repo_doctor = repo_doctor;
    }

    @Transactional
    @RequestMapping(value = "/docteurlist", method = RequestMethod.GET)
    public String displayDoctorList(@RequestParam(required=false) String user, 
                                    @RequestParam(required = false) String act, Model model) {
        String username = "-1";
        String action = "-1";

        if (user != null) username = user;
        if (action != null) action = act;

        if (!username.equals("-1") && action.equals("delete")) {
            repo_doctor.deleteByUsername(username);
        }
        else if (!username.equals("-1") && action.equals("modify")) {
            Docteur doctorToModify = repo_doctor.findByUsername(username);

            model.addAttribute("doctorToModify", doctorToModify);
            return "modifydocteur";
        }
        
        Iterable<Docteur> doctors = repo_doctor.findAll();
        model.addAttribute("dl", doctors);

        return "docteurList";
    }
}