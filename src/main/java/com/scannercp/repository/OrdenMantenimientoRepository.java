package com.scannercp.repository;

import com.scannercp.model.OrdenMantenimiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdenMantenimientoRepository
        extends JpaRepository<OrdenMantenimiento, Long> {

    /*
     * =========================================================
     * BUSCAR ORDEN POR INSPECCIÓN
     * =========================================================
     */

    Optional<OrdenMantenimiento> findByInspeccionIdInspeccion(
            Long idInspeccion);

    /*
     * =========================================================
     * VERIFICAR SI YA EXISTE ORDEN PARA UNA INSPECCIÓN
     * =========================================================
     */

    boolean existsByInspeccionIdInspeccion(
            Long idInspeccion);

    /*
     * =========================================================
     * LISTAR ÓRDENES DE UNA PIEZA
     * =========================================================
     */

    List<OrdenMantenimiento> findByPiezaIdPiezaOrderByFechaCreacionDesc(
            Long idPieza);

}
