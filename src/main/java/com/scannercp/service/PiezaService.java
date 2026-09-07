package com.scannercp.service;

import com.scannercp.model.Pieza;
import com.scannercp.repository.PiezaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiezaService {

    private final PiezaRepository piezaRepository;

    public PiezaService(PiezaRepository piezaRepository) {
        this.piezaRepository = piezaRepository;
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
                                "La pieza no existe"
                        )
                );
    }
}