package com.prueba.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MantenimientoController {

    @GetMapping("/mantenimientos")
    public String mantenimientos() {

        return "mantenimientos";
    }

}
