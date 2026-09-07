package com.scannercp.service;

import com.scannercp.model.Usuario;
import com.scannercp.model.enums.EstadoUsuario;
import com.scannercp.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.scannercp.model.enums.RolUsuario;
import com.scannercp.dto.UsuarioEdicionForm;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<Usuario> buscarPorCodigo(String codigo) {
        return usuarioRepository.findByCodigo(codigo);
    }

    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existePorCodigo(String codigo) {
        return usuarioRepository.existsByCodigo(codigo);
    }

    public long contarUsuarios() {
        return usuarioRepository.count();
    }

    public long contarActivos() {
        return usuarioRepository.countByEstado(EstadoUsuario.ACTIVO);
    }

    public long contarTecnicos() {
        return usuarioRepository.countByRol(RolUsuario.TECNICO);
    }

    public long contarSupervisores() {
        return usuarioRepository.countByRol(RolUsuario.SUPERVISOR);
    }

    // Metodo de creacion de usuarios
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {

        String correoNormalizado = usuario.getCorreo().trim().toLowerCase(Locale.ROOT);

        String codigoNormalizado = usuario.getCodigo().trim();

        if (usuarioRepository.existsByCorreo(correoNormalizado)) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con ese correo");
        }

        if (usuarioRepository.existsByCodigo(codigoNormalizado)) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con ese código");
        }

        if (usuario.getPassword() == null ||
                usuario.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "La contraseña es obligatoria");
        }

        usuario.setCorreo(correoNormalizado);
        usuario.setCodigo(codigoNormalizado);

        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword()));

        if (usuario.getEstado() == null) {
            usuario.setEstado(EstadoUsuario.ACTIVO);
        }

        return usuarioRepository.save(usuario);
    }

    // Metodo de actualizacion de usuarios
    @Transactional
    public Usuario actualizarUsuario(
            Long idUsuario,
            UsuarioEdicionForm formulario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El usuario no existe"));

        String correoNormalizado = formulario.getCorreo()
                .trim()
                .toLowerCase(Locale.ROOT);

        String codigoNormalizado = formulario.getCodigo().trim();

        // Verificar correo duplicado,
        // permitiendo conservar el correo del propio usuario
        usuarioRepository.findByCorreo(correoNormalizado)
                .ifPresent(usuarioConCorreo -> {

                    if (!usuarioConCorreo.getIdUsuario()
                            .equals(idUsuario)) {

                        throw new IllegalArgumentException(
                                "Ya existe otro usuario registrado con ese correo");
                    }
                });

        // Verificar código duplicado,
        // permitiendo conservar el código del propio usuario
        usuarioRepository.findByCodigo(codigoNormalizado)
                .ifPresent(usuarioConCodigo -> {

                    if (!usuarioConCodigo.getIdUsuario()
                            .equals(idUsuario)) {

                        throw new IllegalArgumentException(
                                "Ya existe otro usuario registrado con ese código");
                    }
                });

        usuario.setCodigo(codigoNormalizado);
        usuario.setNombres(formulario.getNombres().trim());
        usuario.setApellidos(formulario.getApellidos().trim());
        usuario.setCorreo(correoNormalizado);
        usuario.setRol(formulario.getRol());
        usuario.setEstado(formulario.getEstado());

        return usuarioRepository.save(usuario);
    }
}
