package com.prueba.web.controller;

import com.prueba.web.service.PiezaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PiezaController {

    private final PiezaService piezaService;

    public PiezaController(PiezaService piezaService) {
        this.piezaService = piezaService;
    }


    @GetMapping("/piezas")
    public String piezas(Model model) {

        model.addAttribute(
                "piezas",
                piezaService.listarPiezas()
        );

        return "piezas";
    }


    @GetMapping("/piezas/{id}")
    public String detallePieza(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "piezaId",
                id
        );

        return "detalle-pieza";
    }

}