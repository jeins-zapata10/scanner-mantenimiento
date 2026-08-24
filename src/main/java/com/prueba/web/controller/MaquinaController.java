package com.prueba.web.controller;

import com.prueba.web.service.MaquinaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaquinaController {

    private final MaquinaService maquinaService;

    public MaquinaController(MaquinaService maquinaService) {
        this.maquinaService = maquinaService;
    }

    @GetMapping("/maquinas")
    public String listarMaquinas(Model model) {

        model.addAttribute(
            "maquinas",
            maquinaService.listarMaquinas()
        );

        return "maquinas";
    }
}
