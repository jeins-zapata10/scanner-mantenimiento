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
import com.scannercp.dto.UsuarioEdicionForm;
import com.scannercp.model.enums.EstadoUsuario;
import org.springframework.web.bind.annotation.PathVariable;

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
                                usuarioService.listarUsuarios());

                model.addAttribute(
                                "totalUsuarios",
                                usuarioService.contarUsuarios());

                model.addAttribute(
                                "totalActivos",
                                usuarioService.contarActivos());

                model.addAttribute(
                                "totalTecnicos",
                                usuarioService.contarTecnicos());

                model.addAttribute(
                                "totalSupervisores",
                                usuarioService.contarSupervisores());

                return "usuarios";
        }

        // Controlador para mostrar el formulario de creacion de usuarios nuevos
        @GetMapping("/usuarios/nuevo")
        public String mostrarFormularioUsuario(Model model) {

                model.addAttribute(
                                "usuarioForm",
                                new UsuarioRegistroForm());

                model.addAttribute(
                                "roles",
                                RolUsuario.values());

                return "usuario-form";
        }

        // Controlador para mostrar el formulario de edicion del usuario
        @GetMapping("/usuarios/{id}/editar")
        public String mostrarFormularioEditarUsuario(
                        @PathVariable Long id,
                        Model model) {

                Usuario usuario = usuarioService.buscarPorId(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "El usuario no existe"));

                UsuarioEdicionForm formulario = new UsuarioEdicionForm();

                formulario.setCodigo(usuario.getCodigo());
                formulario.setNombres(usuario.getNombres());
                formulario.setApellidos(usuario.getApellidos());
                formulario.setCorreo(usuario.getCorreo());
                formulario.setRol(usuario.getRol());
                formulario.setEstado(usuario.getEstado());

                model.addAttribute(
                                "usuarioForm",
                                formulario);

                model.addAttribute(
                                "usuarioId",
                                usuario.getIdUsuario());

                model.addAttribute(
                                "roles",
                                RolUsuario.values());

                model.addAttribute(
                                "estados",
                                EstadoUsuario.values());

                return "usuario-editar";
        }

        @PostMapping("/usuarios/{id}/editar")
        public String actualizarUsuario(
                        @PathVariable Long id,

                        @Valid @ModelAttribute("usuarioForm") UsuarioEdicionForm formulario,

                        BindingResult bindingResult,

                        Model model,

                        RedirectAttributes redirectAttributes) {

                // Si las validaciones del DTO fallan
                if (bindingResult.hasErrors()) {

                        model.addAttribute(
                                        "usuarioId",
                                        id);

                        model.addAttribute(
                                        "roles",
                                        RolUsuario.values());

                        model.addAttribute(
                                        "estados",
                                        EstadoUsuario.values());

                        return "usuario-editar";
                }

                try {

                        usuarioService.actualizarUsuario(
                                        id,
                                        formulario);

                } catch (IllegalArgumentException e) {

                        bindingResult.reject(
                                        "usuario.error",
                                        e.getMessage());

                        model.addAttribute(
                                        "usuarioId",
                                        id);

                        model.addAttribute(
                                        "roles",
                                        RolUsuario.values());

                        model.addAttribute(
                                        "estados",
                                        EstadoUsuario.values());

                        return "usuario-editar";
                }

                redirectAttributes.addFlashAttribute(
                                "mensajeExito",
                                "Usuario actualizado correctamente");

                return "redirect:/usuarios";
        }

        @PostMapping("/usuarios")
        public String crearUsuario(
                        @Valid @ModelAttribute("usuarioForm") UsuarioRegistroForm formulario,

                        BindingResult bindingResult,

                        Model model,

                        RedirectAttributes redirectAttributes) {

                if (bindingResult.hasErrors()) {

                        model.addAttribute(
                                        "roles",
                                        RolUsuario.values());

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
                                        e.getMessage());

                        model.addAttribute(
                                        "roles",
                                        RolUsuario.values());

                        return "usuario-form";
                }

                redirectAttributes.addFlashAttribute(
                                "mensajeExito",
                                "Usuario registrado correctamente");

                return "redirect:/usuarios";
        }
}