package com.scannercp.repository;

import com.scannercp.model.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PiezaRepository extends JpaRepository<Pieza, Long> {

}