package com.scannercp.service;

import com.scannercp.model.Inspeccion;
import com.scannercp.model.OrdenMantenimiento;
import com.scannercp.model.enums.EstadoOrdenMantenimiento;
import com.scannercp.repository.OrdenMantenimientoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdenMantenimientoService {

    private final OrdenMantenimientoRepository ordenRepository;

    public OrdenMantenimientoService(
            OrdenMantenimientoRepository ordenRepository) {

        this.ordenRepository = ordenRepository;
    }

    /*
     * =========================================================
     * CREAR ORDEN DESDE UNA INSPECCIÓN
     * =========================================================
     */

    @Transactional
    public OrdenMantenimiento crearDesdeInspeccion(
            Inspeccion inspeccion) {

        /*
         * -----------------------------------------------------
         * VALIDAR INSPECCIÓN
         * -----------------------------------------------------
         */

        if (inspeccion == null
                || inspeccion.getIdInspeccion() == null) {

            throw new IllegalArgumentException(
                    "La inspección debe estar guardada antes de crear la orden");
        }

        /*
         * -----------------------------------------------------
         * EVITAR ORDEN DUPLICADA
         * -----------------------------------------------------
         */

        if (ordenRepository
                .existsByInspeccionIdInspeccion(
                        inspeccion.getIdInspeccion())) {

            throw new IllegalArgumentException(
                    "Ya existe una orden para esta inspección");
        }

        /*
         * -----------------------------------------------------
         * VALIDAR PRIORIDAD
         * -----------------------------------------------------
         */

        if (inspeccion.getPrioridad() == null) {

            throw new IllegalArgumentException(
                    "La inspección no tiene prioridad de mantenimiento");
        }

        /*
         * -----------------------------------------------------
         * CREAR ORDEN
         * -----------------------------------------------------
         */

        OrdenMantenimiento orden = new OrdenMantenimiento();

        orden.setInspeccion(
                inspeccion);

        orden.setPieza(
                inspeccion.getPieza());

        orden.setUsuarioSolicitante(
                inspeccion.getUsuario());

        orden.setPrioridad(
                inspeccion.getPrioridad());

        orden.setEstado(
                EstadoOrdenMantenimiento.PENDIENTE);

        /*
         * -----------------------------------------------------
         * GUARDAR PRIMERO PARA OBTENER ID
         * -----------------------------------------------------
         */

        String codigo = "OM-" +
                java.util.UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        orden.setCodigo(
                codigo);

        return ordenRepository.save(
                orden);
    }

    /*
     * =========================================================
     * LISTAR ÓRDENES DE UNA PIEZA
     * =========================================================
     */

    public List<OrdenMantenimiento> listarPorPieza(
            Long idPieza) {

        return ordenRepository
                .findByPiezaIdPiezaOrderByFechaCreacionDesc(
                        idPieza);
    }

}