package com.scannercp.model;

import com.scannercp.model.enums.EstadoInspeccion;
import com.scannercp.model.enums.PrioridadMantenimiento;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspeccion")
public class Inspeccion {

    /*
     * =========================================================
     * ID
     * =========================================================
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inspeccion")
    private Long idInspeccion;

    /*
     * =========================================================
     * PIEZA INSPECCIONADA
     * =========================================================
     */

    @ManyToOne
    @JoinColumn(name = "id_pieza", nullable = false)
    private Pieza pieza;

    /*
     * =========================================================
     * USUARIO QUE REALIZA LA INSPECCIÓN
     * =========================================================
     */

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /*
     * =========================================================
     * OBSERVACIÓN
     * =========================================================
     */

    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;

    /*
     * =========================================================
     * REQUIERE MANTENIMIENTO
     * =========================================================
     */

    @Column(name = "requiere_mantenimiento", nullable = false)
    private boolean requiereMantenimiento;

    /*
     * =========================================================
     * PRIORIDAD
     * =========================================================
     *
     * Puede ser null cuando NO requiere mantenimiento.
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", length = 20)
    private PrioridadMantenimiento prioridad;

    /*
     * =========================================================
     * ESTADO
     * =========================================================
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_inicial", nullable = false, length = 30)
    private EstadoInspeccion estadoInicial;

    /*
     * =========================================================
     * FECHA Y HORA
     * =========================================================
     *
     * MySQL genera automáticamente este valor.
     */

    @Column(name = "fecha_hora", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaHora;

    /*
     * =========================================================
     * CONSTRUCTOR
     * =========================================================
     */

    public Inspeccion() {
    }

    /*
     * =========================================================
     * GETTERS Y SETTERS
     * =========================================================
     */

    public Long getIdInspeccion() {
        return idInspeccion;
    }

    public void setIdInspeccion(Long idInspeccion) {
        this.idInspeccion = idInspeccion;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isRequiereMantenimiento() {
        return requiereMantenimiento;
    }

    public void setRequiereMantenimiento(
            boolean requiereMantenimiento) {

        this.requiereMantenimiento = requiereMantenimiento;
    }

    public PrioridadMantenimiento getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(
            PrioridadMantenimiento prioridad) {

        this.prioridad = prioridad;
    }

    public EstadoInspeccion getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(
            EstadoInspeccion estadoInicial) {

        this.estadoInicial = estadoInicial;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

}