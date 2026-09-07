package com.scannercp.repository;

import com.scannercp.model.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {

    Optional<Maquina> findByCodigo(String codigo);

    Optional<Maquina> findByNumeroSerie(String numeroSerie);

    boolean existsByCodigo(String codigo);

    boolean existsByNumeroSerie(String numeroSerie);
}
