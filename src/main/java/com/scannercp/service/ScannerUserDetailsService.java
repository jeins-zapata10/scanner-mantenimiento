package com.scannercp.service;

import com.scannercp.model.Usuario;
import com.scannercp.model.enums.EstadoUsuario;
import com.scannercp.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ScannerUserDetailsService
        implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public ScannerUserDetailsService(
            UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    /*
     * =========================================================
     * BUSCAR USUARIO PARA SPRING SECURITY
     * =========================================================
     */

    @Override
    public UserDetails loadUserByUsername(
            String correo)
            throws UsernameNotFoundException {

        String correoNormalizado = correo
                .trim()
                .toLowerCase(Locale.ROOT);

        Usuario usuario = usuarioRepository
                .findByCorreo(correoNormalizado)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Usuario no encontrado"));

        return User
                .withUsername(
                        usuario.getCorreo())

                .password(
                        usuario.getPassword())

                .roles(
                        usuario.getRol().name())

                .disabled(
                        usuario.getEstado() != EstadoUsuario.ACTIVO)

                .build();
    }
}