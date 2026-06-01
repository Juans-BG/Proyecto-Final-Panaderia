package com.panaderia.panaderia.repository;

import com.panaderia.panaderia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository
        extends JpaRepository<Cliente, Long> {
}