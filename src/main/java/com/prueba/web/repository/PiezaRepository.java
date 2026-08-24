package com.prueba.web.repository;

import com.prueba.web.model.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PiezaRepository extends JpaRepository<Pieza, Long> {

}