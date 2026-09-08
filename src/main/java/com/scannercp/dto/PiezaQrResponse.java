package com.scannercp.dto;

public class PiezaQrResponse {

    private Long idPieza;

    private String codigo;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String fabricante;
    private String referencia;
    private String estado;

    private Long idMaquina;
    private String codigoMaquina;
    private String nombreMaquina;
    private String areaMaquina;
    private String ubicacionMaquina;

    public PiezaQrResponse() {
    }

    public Long getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(Long idPieza) {
        this.idPieza = idPieza;
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

    public Long getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Long idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina) {
        this.codigoMaquina = codigoMaquina;
    }

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }

    public String getAreaMaquina() {
        return areaMaquina;
    }

    public void setAreaMaquina(String areaMaquina) {
        this.areaMaquina = areaMaquina;
    }

    public String getUbicacionMaquina() {
        return ubicacionMaquina;
    }

    public void setUbicacionMaquina(String ubicacionMaquina) {
        this.ubicacionMaquina = ubicacionMaquina;
    }
}
