package com.scannercp.service;

import com.scannercp.model.Usuario;
import com.scannercp.model.enums.EstadoUsuario;
import com.scannercp.model.enums.RolUsuario;
import com.scannercp.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();

        usuarioService = new UsuarioService(
                usuarioRepository,
                passwordEncoder
        );
    }

    @Test
    void crearUsuarioDebeCifrarPasswordYNormalizarDatos() {

        Usuario usuario = new Usuario();

        usuario.setCodigo(" TEC001 ");
        usuario.setNombres("Carlos");
        usuario.setApellidos("Perez");
        usuario.setCorreo(" CARLOS@EMPRESA.COM ");
        usuario.setPassword("MiClave123");
        usuario.setRol(RolUsuario.TECNICO);

        when(usuarioRepository.existsByCorreo("carlos@empresa.com"))
                .thenReturn(false);

        when(usuarioRepository.existsByCodigo("TEC001"))
                .thenReturn(false);

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Usuario usuarioGuardado =
                usuarioService.crearUsuario(usuario);

        assertEquals(
                "carlos@empresa.com",
                usuarioGuardado.getCorreo()
        );

        assertEquals(
                "TEC001",
                usuarioGuardado.getCodigo()
        );

        assertEquals(
                EstadoUsuario.ACTIVO,
                usuarioGuardado.getEstado()
        );

        assertNotEquals(
                "MiClave123",
                usuarioGuardado.getPassword()
        );

        assertTrue(
                passwordEncoder.matches(
                        "MiClave123",
                        usuarioGuardado.getPassword()
                )
        );

        verify(usuarioRepository, times(1))
                .save(usuario);
    }
}
