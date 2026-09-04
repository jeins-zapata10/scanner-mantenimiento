package com.scannercp.repository;

import com.scannercp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scannercp.model.enums.EstadoUsuario;
import com.scannercp.model.enums.RolUsuario;



import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCodigo(String codigo);

    boolean existsByCorreo(String correo);

    boolean existsByCodigo(String codigo);

    long countByEstado(EstadoUsuario estado);

    long countByRol(RolUsuario rol);

}
