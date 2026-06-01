package com.panaderia.panaderia.repository;

import com.panaderia.panaderia.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository
        extends JpaRepository<Venta, Long> {
}