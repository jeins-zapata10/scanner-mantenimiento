package com.scannercp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InspeccionController {

    @GetMapping("/inspecciones")
    public String inspecciones() {

        return "inspecciones";
    }

}