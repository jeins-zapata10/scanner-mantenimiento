package com.scannercp.service;

import com.scannercp.model.Usuario;
import com.scannercp.model.enums.EstadoUsuario;
import com.scannercp.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Usuario crearUsuario(Usuario usuario) {

        String correoNormalizado =
                usuario.getCorreo().trim().toLowerCase(Locale.ROOT);

        String codigoNormalizado =
                usuario.getCodigo().trim();

        if (usuarioRepository.existsByCorreo(correoNormalizado)) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con ese correo"
            );
        }

        if (usuarioRepository.existsByCodigo(codigoNormalizado)) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con ese código"
            );
        }

        if (usuario.getPassword() == null ||
                usuario.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "La contraseña es obligatoria"
            );
        }

        usuario.setCorreo(correoNormalizado);
        usuario.setCodigo(codigoNormalizado);

        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        if (usuario.getEstado() == null) {
            usuario.setEstado(EstadoUsuario.ACTIVO);
        }

        return usuarioRepository.save(usuario);
    }
}
