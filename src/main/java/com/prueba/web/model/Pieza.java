package com.prueba.web.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pieza")
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pieza")
    private Long idPieza;

    @ManyToOne
    @JoinColumn(name = "id_maquina", nullable = false)
    private Maquina maquina;

    @Column(name = "codigo", nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ubicacion", length = 150)
    private String ubicacion;

    @Column(name = "fabricante", length = 100)
    private String fabricante;

    @Column(name = "referencia", length = 100)
    private String referencia;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Column(name = "codigo_qr", nullable = false, unique = true, length = 255)
    private String codigoQr;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    public Pieza() {
    }

    public Long getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(Long idPieza) {
        this.idPieza = idPieza;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoQr() {
        return codigoQr;
    }

    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}