package com.scannercp.controller;

import com.scannercp.dto.MaquinaRegistroForm;
import com.scannercp.service.MaquinaService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MaquinaController {

    private final MaquinaService maquinaService;

    public MaquinaController(MaquinaService maquinaService) {
        this.maquinaService = maquinaService;
    }

    /*
     * =========================================================
     * LISTAR MÁQUINAS
     * =========================================================
     */

    @GetMapping("/maquinas")
    public String listarMaquinas(Model model) {

        model.addAttribute(
                "maquinas",
                maquinaService.listarMaquinas());

        return "maquinas";
    }

    /*
     * =========================================================
     * MOSTRAR FORMULARIO NUEVA MÁQUINA
     * =========================================================
     */

    @GetMapping("/maquinas/nueva")
    public String mostrarFormularioNuevaMaquina(Model model) {

        model.addAttribute(
                "maquinaForm",
                new MaquinaRegistroForm());

        agregarDatosFormulario(model);

        return "maquina-form";
    }

    /*
     * =========================================================
     * GUARDAR NUEVA MÁQUINA
     * =========================================================
     */

    @PostMapping("/maquinas")
    public String crearMaquina(

            @Valid @ModelAttribute("maquinaForm") MaquinaRegistroForm formulario,

            BindingResult bindingResult,

            Model model,

            RedirectAttributes redirectAttributes) {

        /*
         * Validaciones del DTO
         */

        if (bindingResult.hasErrors()) {

            agregarDatosFormulario(model);

            return "maquina-form";
        }

        /*
         * Validaciones de negocio
         */

        try {

            maquinaService.crearMaquina(formulario);

        } catch (IllegalArgumentException e) {

            bindingResult.reject(
                    "maquina.error",
                    e.getMessage());

            agregarDatosFormulario(model);

            return "maquina-form";
        }

        /*
         * Mensaje de éxito
         */

        redirectAttributes.addFlashAttribute(
                "mensajeExito",
                "Máquina registrada correctamente");

        return "redirect:/maquinas";
    }

    /*
     * =========================================================
     * VER DETALLE DE UNA MÁQUINA
     * =========================================================
     */

    @GetMapping("/maquinas/{id}")
    public String verDetalleMaquina(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "maquina",
                maquinaService.buscarPorId(id));

        return "maquina-detalle";
    }

    /*
     * =========================================================
     * DATOS AUXILIARES DEL FORMULARIO
     * =========================================================
     */

    private void agregarDatosFormulario(Model model) {

        model.addAttribute(
                "estados",
                List.of(
                        "OPERATIVA",
                        "MANTENIMIENTO",
                        "FUERA_DE_SERVICIO"));
    }
}