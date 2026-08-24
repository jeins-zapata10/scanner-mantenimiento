package com.prueba.web.service;

import com.prueba.web.model.Pieza;
import com.prueba.web.repository.PiezaRepository;

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