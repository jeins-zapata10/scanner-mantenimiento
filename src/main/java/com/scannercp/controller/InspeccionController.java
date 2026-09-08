package com.scannercp.controller;

import com.scannercp.dto.InspeccionForm;
import com.scannercp.model.Pieza;
import com.scannercp.model.enums.PrioridadMantenimiento;
import com.scannercp.service.InspeccionService;
import com.scannercp.service.PiezaService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inspecciones")
public class InspeccionController {

    private final InspeccionService inspeccionService;

    private final PiezaService piezaService;

    public InspeccionController(
            InspeccionService inspeccionService,
            PiezaService piezaService) {

        this.inspeccionService = inspeccionService;

        this.piezaService = piezaService;
    }

    /*
     * =========================================================
     * MOSTRAR FORMULARIO DE INSPECCIÓN
     * =========================================================
     */

    @GetMapping("/nueva/{idPieza}")
    public String mostrarFormulario(
            @PathVariable Long idPieza,
            Model model) {

        Pieza pieza = piezaService.buscarPorId(idPieza);

        model.addAttribute(
                "pieza",
                pieza);

        model.addAttribute(
                "inspeccionForm",
                new InspeccionForm());

        model.addAttribute(
                "prioridades",
                PrioridadMantenimiento.values());

        return "inspeccion-form";
    }

    /*
     * =========================================================
     * GUARDAR INSPECCIÓN
     * =========================================================
     */

    @PostMapping("/nueva/{idPieza}")
    public String registrarInspeccion(
            @PathVariable Long idPieza,

            @Valid @ModelAttribute("inspeccionForm") InspeccionForm formulario,

            BindingResult bindingResult,

            Authentication authentication,

            Model model,

            RedirectAttributes redirectAttributes) {

        /*
         * -----------------------------------------------------
         * VALIDACIONES DEL FORMULARIO
         * -----------------------------------------------------
         */

        if (bindingResult.hasErrors()) {

            cargarDatosFormulario(
                    idPieza,
                    model);

            return "inspeccion-form";
        }

        /*
         * -----------------------------------------------------
         * REGISTRAR INSPECCIÓN
         * -----------------------------------------------------
         */

        try {

            inspeccionService.registrarInspeccion(
                    idPieza,
                    authentication.getName(),
                    formulario);

        } catch (IllegalArgumentException ex) {

            cargarDatosFormulario(
                    idPieza,
                    model);

            model.addAttribute(
                    "error",
                    ex.getMessage());

            return "inspeccion-form";
        }

        /*
         * -----------------------------------------------------
         * CONFIRMACIÓN
         * -----------------------------------------------------
         */

        redirectAttributes.addFlashAttribute(
                "mensaje",
                "Inspección registrada correctamente");

        return "redirect:/scanner";
    }

    /*
     * =========================================================
     * DATOS AUXILIARES DEL FORMULARIO
     * =========================================================
     */

    private void cargarDatosFormulario(
            Long idPieza,
            Model model) {

        model.addAttribute(
                "pieza",
                piezaService.buscarPorId(idPieza));

        model.addAttribute(
                "prioridades",
                PrioridadMantenimiento.values());
    }

}