package com.scannercp.controller;

import com.scannercp.dto.PiezaRegistroForm;
import com.scannercp.service.MaquinaService;
import com.scannercp.service.PiezaService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PiezaController {

    private final PiezaService piezaService;
    private final MaquinaService maquinaService;

    public PiezaController(
            PiezaService piezaService,
            MaquinaService maquinaService) {

        this.piezaService = piezaService;
        this.maquinaService = maquinaService;
    }

    /*
     * =========================================================
     * LISTAR PIEZAS
     * =========================================================
     */

    @GetMapping("/piezas")
    public String piezas(Model model) {

        model.addAttribute(
                "piezas",
                piezaService.listarPiezas());

        return "piezas";
    }

    /*
     * =========================================================
     * MOSTRAR FORMULARIO NUEVA PIEZA
     * =========================================================
     */

    @GetMapping("/piezas/nueva")
    public String mostrarFormularioNuevaPieza(Model model) {

        model.addAttribute(
                "piezaForm",
                new PiezaRegistroForm());

        agregarDatosFormulario(model);

        return "pieza-form";
    }

    /*
     * =========================================================
     * GUARDAR NUEVA PIEZA
     * =========================================================
     */

    @PostMapping("/piezas")
    public String crearPieza(

            @Valid @ModelAttribute("piezaForm") PiezaRegistroForm formulario,

            BindingResult bindingResult,

            Model model,

            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            agregarDatosFormulario(model);

            return "pieza-form";
        }

        try {

            piezaService.crearPieza(formulario);

        } catch (IllegalArgumentException e) {

            bindingResult.reject(
                    "pieza.error",
                    e.getMessage());

            agregarDatosFormulario(model);

            return "pieza-form";
        }

        redirectAttributes.addFlashAttribute(
                "mensajeExito",
                "Pieza registrada correctamente");

        return "redirect:/piezas";
    }

    /*
     * =========================================================
     * DETALLE DE PIEZA
     * =========================================================
     */

    @GetMapping("/piezas/{id}")
    public String detallePieza(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "pieza",
                piezaService.buscarPorId(id));

        return "detalle-pieza";
    }

    /*
     * =========================================================
     * DATOS DEL FORMULARIO
     * =========================================================
     */

    private void agregarDatosFormulario(Model model) {

        model.addAttribute(
                "maquinas",
                maquinaService.listarMaquinas());

        model.addAttribute(
                "estados",
                List.of(
                        "OPERATIVA",
                        "MANTENIMIENTO",
                        "FUERA_DE_SERVICIO"));
    }
}