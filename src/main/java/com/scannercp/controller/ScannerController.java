package com.scannercp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScannerController {

    /*
     * =========================================================
     * PANTALLA PRINCIPAL DE ESCANEO
     * =========================================================
     */

    @GetMapping("/scanner")
    public String scanner() {

        return "scanner";
    }
}