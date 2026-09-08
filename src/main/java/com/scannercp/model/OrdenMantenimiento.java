package com.scannercp.model;

import com.scannercp.model.enums.EstadoOrdenMantenimiento;
import com.scannercp.model.enums.PrioridadMantenimiento;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orden_mantenimiento", uniqueConstraints = {
        @UniqueConstraint(name = "uk_orden_codigo", columnNames = "codigo"),
        @UniqueConstraint(name = "uk_orden_inspeccion", columnNames = "id_inspeccion")
})
public class OrdenMantenimiento {

    /*
     * =========================================================
     * ID
     * =========================================================
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long idOrden;

    /*
     * =========================================================
     * CÓDIGO DE LA ORDEN
     * =========================================================
     */

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    /*
     * =========================================================
     * INSPECCIÓN QUE GENERÓ LA ORDEN
     * =========================================================
     *
     * Una inspección puede generar como máximo una orden.
     */

    @OneToOne
    @JoinColumn(name = "id_inspeccion", nullable = false, unique = true)
    private Inspeccion inspeccion;

    /*
     * =========================================================
     * PIEZA
     * =========================================================
     */

    @ManyToOne
    @JoinColumn(name = "id_pieza", nullable = false)
    private Pieza pieza;

    /*
     * =========================================================
     * USUARIO SOLICITANTE
     * =========================================================
     */

    @ManyToOne
    @JoinColumn(name = "id_usuario_solicitante", nullable = false)
    private Usuario usuarioSolicitante;

    /*
     * =========================================================
     * ESTADO
     * =========================================================
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoOrdenMantenimiento estado;

    /*
     * =========================================================
     * PRIORIDAD
     * =========================================================
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false, length = 20)
    private PrioridadMantenimiento prioridad;

    /*
     * =========================================================
     * FECHA DE CREACIÓN
     * =========================================================
     *
     * MySQL genera automáticamente este valor.
     */

    @Column(name = "fecha_creacion", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /*
     * =========================================================
     * CONSTRUCTOR
     * =========================================================
     */

    public OrdenMantenimiento() {
    }

    /*
     * =========================================================
     * GETTERS Y SETTERS
     * =========================================================
     */

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Inspeccion getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(
            Inspeccion inspeccion) {

        this.inspeccion = inspeccion;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(
            Pieza pieza) {

        this.pieza = pieza;
    }

    public Usuario getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(
            Usuario usuarioSolicitante) {

        this.usuarioSolicitante = usuarioSolicitante;
    }

    public EstadoOrdenMantenimiento getEstado() {
        return estado;
    }

    public void setEstado(
            EstadoOrdenMantenimiento estado) {

        this.estado = estado;
    }

    public PrioridadMantenimiento getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(
            PrioridadMantenimiento prioridad) {

        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

}
