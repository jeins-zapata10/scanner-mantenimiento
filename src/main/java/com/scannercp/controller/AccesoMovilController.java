package com.scannercp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesoMovilController {

    /*
     * =========================================================
     * ACCESO DESDE PC PARA USUARIOS DE CAMPO
     * =========================================================
     */

    @GetMapping("/acceso-movil")
    public String accesoMovil() {

        return "acceso-movil";
    }
}
