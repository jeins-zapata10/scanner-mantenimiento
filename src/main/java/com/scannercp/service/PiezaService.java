package com.scannercp.service;

import com.scannercp.dto.PiezaRegistroForm;
import com.scannercp.model.Maquina;
import com.scannercp.model.Pieza;
import com.scannercp.repository.MaquinaRepository;
import com.scannercp.repository.PiezaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class PiezaService {

    private final PiezaRepository piezaRepository;
    private final MaquinaRepository maquinaRepository;

    public PiezaService(
            PiezaRepository piezaRepository,
            MaquinaRepository maquinaRepository) {

        this.piezaRepository = piezaRepository;
        this.maquinaRepository = maquinaRepository;
    }

    /*
     * =========================================================
     * LISTAR TODAS LAS PIEZAS
     * =========================================================
     */

    public List<Pieza> listarPiezas() {
        return piezaRepository.findAll();
    }

    /*
     * =========================================================
     * LISTAR PIEZAS DE UNA MÁQUINA
     * =========================================================
     */

    public List<Pieza> listarPiezasPorMaquina(Long idMaquina) {

        return piezaRepository
                .findByMaquinaIdMaquina(idMaquina);
    }

    /*
     * =========================================================
     * CONTAR PIEZAS DE UNA MÁQUINA
     * =========================================================
     */

    public long contarPiezasPorMaquina(Long idMaquina) {

        return piezaRepository
                .countByMaquinaIdMaquina(idMaquina);
    }

    /*
     * =========================================================
     * BUSCAR PIEZA POR ID
     * =========================================================
     */

    public Pieza buscarPorId(Long idPieza) {

        return piezaRepository.findById(idPieza)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "La pieza no existe"));
    }

    /*
     * =========================================================
     * CREAR PIEZA
     * =========================================================
     */

    @Transactional
    public Pieza crearPieza(PiezaRegistroForm formulario) {

        Maquina maquina = maquinaRepository
                .findById(formulario.getIdMaquina())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "La máquina seleccionada no existe"));

        String codigoNormalizado = formulario.getCodigo()
                .trim()
                .toUpperCase(Locale.ROOT);

        if (piezaRepository.existsByCodigo(codigoNormalizado)) {

            throw new IllegalArgumentException(
                    "Ya existe una pieza registrada con ese código");
        }

        Pieza pieza = new Pieza();

        pieza.setMaquina(maquina);

        pieza.setCodigo(codigoNormalizado);

        pieza.setNombre(
                formulario.getNombre().trim());

        pieza.setUbicacion(
                normalizarOpcional(formulario.getUbicacion()));

        pieza.setFabricante(
                normalizarOpcional(formulario.getFabricante()));

        pieza.setReferencia(
                normalizarOpcional(formulario.getReferencia()));

        pieza.setDescripcion(
                normalizarOpcional(formulario.getDescripcion()));

        pieza.setEstado(
                formulario.getEstado()
                        .trim()
                        .toUpperCase(Locale.ROOT));

        /*
         * El usuario NO escribe el QR.
         * ScannerCP genera un identificador único.
         */

        pieza.setCodigoQr(
                generarCodigoQr());

        return piezaRepository.save(pieza);
    }

    /*
     * =========================================================
     * GENERAR IDENTIFICADOR QR
     * =========================================================
     */

    private String generarCodigoQr() {

        String codigoQr;

        do {

            codigoQr = "SCANNERCP:PZA:"
                    + UUID.randomUUID();

        } while (piezaRepository.existsByCodigoQr(codigoQr));

        return codigoQr;
    }

    /*
     * =========================================================
     * NORMALIZAR CAMPOS OPCIONALES
     * =========================================================
     */

    private String normalizarOpcional(String valor) {

        if (valor == null) {
            return null;
        }

        String valorNormalizado = valor.trim();

        if (valorNormalizado.isEmpty()) {
            return null;
        }

        return valorNormalizado;
    }
}