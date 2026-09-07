package com.scannercp.repository;

import com.scannercp.model.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PiezaRepository extends JpaRepository<Pieza, Long> {

    List<Pieza> findByMaquinaIdMaquina(Long idMaquina);

    long countByMaquinaIdMaquina(Long idMaquina);

    Optional<Pieza> findByCodigo(String codigo);

    Optional<Pieza> findByCodigoQr(String codigoQr);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoQr(String codigoQr);
}