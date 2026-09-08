package com.scannercp.service;

import com.scannercp.dto.InspeccionForm;
import com.scannercp.model.Inspeccion;
import com.scannercp.model.Pieza;
import com.scannercp.model.Usuario;
import com.scannercp.model.enums.EstadoInspeccion;
import com.scannercp.repository.InspeccionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class InspeccionService {

        private final InspeccionRepository inspeccionRepository;

        private final PiezaService piezaService;

        private final UsuarioService usuarioService;

        private final OrdenMantenimientoService ordenMantenimientoService;

        public InspeccionService(
                        InspeccionRepository inspeccionRepository,
                        PiezaService piezaService,
                        UsuarioService usuarioService,
                        OrdenMantenimientoService ordenMantenimientoService) {

                this.inspeccionRepository = inspeccionRepository;

                this.piezaService = piezaService;

                this.usuarioService = usuarioService;

                this.ordenMantenimientoService = ordenMantenimientoService;
        }

        /*
         * =========================================================
         * REGISTRAR INSPECCIÓN
         * =========================================================
         */

        @Transactional
        public Inspeccion registrarInspeccion(
                        Long idPieza,
                        String correoUsuario,
                        InspeccionForm formulario) {

                /*
                 * -----------------------------------------------------
                 * VALIDAR PIEZA
                 * -----------------------------------------------------
                 */

                Pieza pieza = piezaService.buscarPorId(idPieza);

                /*
                 * -----------------------------------------------------
                 * OBTENER USUARIO AUTENTICADO
                 * -----------------------------------------------------
                 */

                if (correoUsuario == null
                                || correoUsuario.isBlank()) {

                        throw new IllegalArgumentException(
                                        "No fue posible identificar al usuario autenticado");
                }

                String correoNormalizado = correoUsuario
                                .trim()
                                .toLowerCase(Locale.ROOT);

                Usuario usuario = usuarioService
                                .buscarPorCorreo(correoNormalizado)
                                .orElseThrow(
                                                () -> new IllegalArgumentException(
                                                                "El usuario autenticado no existe"));

                /*
                 * -----------------------------------------------------
                 * VALIDAR RESPUESTA DE MANTENIMIENTO
                 * -----------------------------------------------------
                 */

                if (formulario.getRequiereMantenimiento() == null) {

                        throw new IllegalArgumentException(
                                        "Debes indicar si la pieza requiere mantenimiento");
                }

                boolean requiereMantenimiento = formulario.getRequiereMantenimiento();

                /*
                 * -----------------------------------------------------
                 * CREAR INSPECCIÓN
                 * -----------------------------------------------------
                 */

                Inspeccion inspeccion = new Inspeccion();

                inspeccion.setPieza(pieza);

                inspeccion.setUsuario(usuario);

                inspeccion.setObservacion(
                                normalizarObservacion(
                                                formulario.getObservacion()));

                inspeccion.setRequiereMantenimiento(
                                requiereMantenimiento);

                /*
                 * -----------------------------------------------------
                 * REGLA DE NEGOCIO
                 * -----------------------------------------------------
                 */

                if (requiereMantenimiento) {

                        /*
                         * Si requiere mantenimiento,
                         * la prioridad es obligatoria.
                         */

                        if (formulario.getPrioridad() == null) {

                                throw new IllegalArgumentException(
                                                "Debes seleccionar la prioridad del mantenimiento");
                        }

                        inspeccion.setPrioridad(
                                        formulario.getPrioridad());

                        inspeccion.setEstadoInicial(
                                        EstadoInspeccion.EN_PROCESO);

                } else {

                        /*
                         * Si NO requiere mantenimiento:
                         *
                         * prioridad = null
                         * estado = COMPLETADA
                         */

                        inspeccion.setPrioridad(null);

                        inspeccion.setEstadoInicial(
                                        EstadoInspeccion.COMPLETADA);
                }

                /*
                 * -----------------------------------------------------
                 * GUARDAR
                 * -----------------------------------------------------
                 */

                Inspeccion inspeccionGuardada = inspeccionRepository.save(
                                inspeccion);

                /*
                 * =========================================================
                 * CREAR ORDEN AUTOMÁTICAMENTE
                 * =========================================================
                 */

                if (requiereMantenimiento) {

                        ordenMantenimientoService
                                        .crearDesdeInspeccion(
                                                        inspeccionGuardada);
                }

                return inspeccionGuardada;

        }

        /*
         * =========================================================
         * HISTORIAL DE UNA PIEZA
         * =========================================================
         */

        public List<Inspeccion> listarInspeccionesPorPieza(
                        Long idPieza) {

                return inspeccionRepository
                                .findByPiezaIdPiezaOrderByFechaHoraDesc(
                                                idPieza);
        }

        /*
         * =========================================================
         * NORMALIZAR OBSERVACIÓN
         * =========================================================
         */

        private String normalizarObservacion(
                        String observacion) {

                if (observacion == null) {
                        return null;
                }

                String texto = observacion.trim();

                if (texto.isEmpty()) {
                        return null;
                }

                return texto;
        }

}
