package com.panaderia.panaderia.repository;

import com.panaderia.panaderia.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {
}