package com.scannercp.dto;

import com.scannercp.model.enums.PrioridadMantenimiento;

import jakarta.validation.constraints.NotNull;

public class InspeccionForm {

    /*
     * =========================================================
     * OBSERVACIÓN
     * =========================================================
     */

    private String observacion;

    /*
     * =========================================================
     * REQUIERE MANTENIMIENTO
     * =========================================================
     */

    @NotNull(message = "Debes indicar si la pieza requiere mantenimiento")
    private Boolean requiereMantenimiento;

    /*
     * =========================================================
     * PRIORIDAD
     * =========================================================
     *
     * Solo será obligatoria cuando
     * requiereMantenimiento = true.
     */

    private PrioridadMantenimiento prioridad;

    /*
     * =========================================================
     * GETTERS Y SETTERS
     * =========================================================
     */

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getRequiereMantenimiento() {
        return requiereMantenimiento;
    }

    public void setRequiereMantenimiento(
            Boolean requiereMantenimiento) {

        this.requiereMantenimiento = requiereMantenimiento;
    }

    public PrioridadMantenimiento getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(
            PrioridadMantenimiento prioridad) {

        this.prioridad = prioridad;
    }

}
