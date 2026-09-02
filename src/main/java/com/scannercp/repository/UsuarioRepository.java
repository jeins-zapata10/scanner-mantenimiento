package com.scannercp.repository;

import com.scannercp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCodigo(String codigo);

    boolean existsByCorreo(String correo);

    boolean existsByCodigo(String codigo);
}
