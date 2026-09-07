package com.scannercp.controller;

import com.google.zxing.WriterException;
import com.scannercp.model.Pieza;
import com.scannercp.service.PiezaService;
import com.scannercp.service.QrService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class QrController {

    private final PiezaService piezaService;
    private final QrService qrService;

    public QrController(
            PiezaService piezaService,
            QrService qrService) {

        this.piezaService = piezaService;
        this.qrService = qrService;
    }

    /*
     * =========================================================
     * MOSTRAR QR DE UNA PIEZA
     * =========================================================
     */

    @GetMapping(value = "/piezas/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> obtenerQr(
            @PathVariable Long id)
            throws WriterException, IOException {

        Pieza pieza = piezaService.buscarPorId(id);

        byte[] imagenQr = qrService.generarQrPng(
                pieza.getCodigoQr());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imagenQr);
    }
}