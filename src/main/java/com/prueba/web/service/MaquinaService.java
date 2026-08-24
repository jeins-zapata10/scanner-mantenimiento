package com.prueba.web.service;

import com.prueba.web.model.Maquina;
import com.prueba.web.repository.MaquinaRepository;

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