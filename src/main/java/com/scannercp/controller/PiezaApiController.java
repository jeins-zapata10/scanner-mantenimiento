package com.scannercp.controller;

import com.scannercp.dto.PiezaQrResponse;
import com.scannercp.model.Maquina;
import com.scannercp.model.Pieza;
import com.scannercp.service.PiezaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/piezas")
public class PiezaApiController {

    private final PiezaService piezaService;

    public PiezaApiController(PiezaService piezaService) {
        this.piezaService = piezaService;
    }

    /*
     * =========================================================
     * BUSCAR PIEZA MEDIANTE CÓDIGO QR
     * =========================================================
     */

    @GetMapping("/qr/{codigoQr}")
    public ResponseEntity<PiezaQrResponse> buscarPorQr(
            @PathVariable String codigoQr) {

        Pieza pieza = piezaService.buscarPorCodigoQr(codigoQr);

        Maquina maquina = pieza.getMaquina();

        PiezaQrResponse respuesta = new PiezaQrResponse();

        // PIEZA

        respuesta.setIdPieza(
                pieza.getIdPieza());

        respuesta.setCodigo(
                pieza.getCodigo());

        respuesta.setNombre(
                pieza.getNombre());

        respuesta.setDescripcion(
                pieza.getDescripcion());

        respuesta.setUbicacion(
                pieza.getUbicacion());

        respuesta.setFabricante(
                pieza.getFabricante());

        respuesta.setReferencia(
                pieza.getReferencia());

        respuesta.setEstado(
                pieza.getEstado());

        // MÁQUINA

        respuesta.setIdMaquina(
                maquina.getIdMaquina());

        respuesta.setCodigoMaquina(
                maquina.getCodigo());

        respuesta.setNombreMaquina(
                maquina.getNombre());

        respuesta.setAreaMaquina(
                maquina.getArea());

        respuesta.setUbicacionMaquina(
                maquina.getUbicacion());

        return ResponseEntity.ok(
                respuesta);
    }
}