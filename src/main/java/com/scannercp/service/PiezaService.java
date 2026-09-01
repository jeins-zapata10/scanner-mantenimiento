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

    public List<Pieza> listarPiezas() {
        return piezaRepository.findAll();
    }
}