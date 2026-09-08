package com.scannercp.repository;

import com.scannercp.model.Inspeccion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspeccionRepository
        extends JpaRepository<Inspeccion, Long> {

    /*
     * =========================================================
     * INSPECCIONES POR PIEZA
     * =========================================================
     */

    List<Inspeccion> findByPiezaIdPiezaOrderByFechaHoraDesc(
            Long idPieza);

    /*
     * =========================================================
     * INSPECCIONES POR USUARIO
     * =========================================================
     */

    List<Inspeccion> findByUsuarioIdUsuarioOrderByFechaHoraDesc(
            Long idUsuario);

}