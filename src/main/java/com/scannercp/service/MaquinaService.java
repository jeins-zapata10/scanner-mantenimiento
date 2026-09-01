package com.scannercp.service;

import com.scannercp.model.Maquina;
import com.scannercp.repository.MaquinaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaquinaService {

    private final MaquinaRepository maquinaRepository;

    public MaquinaService(MaquinaRepository maquinaRepository) {
        this.maquinaRepository = maquinaRepository;
    }

    public List<Maquina> listarMaquinas() {
        return maquinaRepository.findAll();
    }

}