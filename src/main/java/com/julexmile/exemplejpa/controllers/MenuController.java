package com.julexmile.exemplejpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MenuController {

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String displayMenu() {
        return "menu";
    }
}
