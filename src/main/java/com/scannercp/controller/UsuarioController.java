package com.scannercp.controller;

import com.scannercp.dto.UsuarioRegistroForm;
import com.scannercp.model.Usuario;
import com.scannercp.model.enums.RolUsuario;
import com.scannercp.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/usuarios")
    public String usuarios(Model model) {

        model.addAttribute(
                "usuarios",
                usuarioService.listarUsuarios()
        );

        model.addAttribute(
                "totalUsuarios",
                usuarioService.contarUsuarios()
        );

        model.addAttribute(
                "totalActivos",
                usuarioService.contarActivos()
        );

        model.addAttribute(
                "totalTecnicos",
                usuarioService.contarTecnicos()
        );

        model.addAttribute(
                "totalSupervisores",
                usuarioService.contarSupervisores()
        );

        return "usuarios";
    }


    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioUsuario(Model model) {

        model.addAttribute(
                "usuarioForm",
                new UsuarioRegistroForm()
        );

        model.addAttribute(
                "roles",
                RolUsuario.values()
        );

        return "usuario-form";
    }


    @PostMapping("/usuarios")
    public String crearUsuario(
            @Valid
            @ModelAttribute("usuarioForm")
            UsuarioRegistroForm formulario,

            BindingResult bindingResult,

            Model model,

            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "roles",
                    RolUsuario.values()
            );

            return "usuario-form";
        }


        Usuario usuario = new Usuario();

        usuario.setCodigo(formulario.getCodigo());
        usuario.setNombres(formulario.getNombres());
        usuario.setApellidos(formulario.getApellidos());
        usuario.setCorreo(formulario.getCorreo());
        usuario.setPassword(formulario.getPassword());
        usuario.setRol(formulario.getRol());


        try {

            usuarioService.crearUsuario(usuario);

        } catch (IllegalArgumentException e) {

            bindingResult.reject(
                    "usuario.error",
                    e.getMessage()
            );

            model.addAttribute(
                    "roles",
                    RolUsuario.values()
            );

            return "usuario-form";
        }


        redirectAttributes.addFlashAttribute(
                "mensajeExito",
                "Usuario registrado correctamente"
        );

        return "redirect:/usuarios";
    }
}