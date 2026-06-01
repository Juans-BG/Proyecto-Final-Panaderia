package com.panaderia.panaderia.service.impl;

import com.panaderia.panaderia.model.DetalleVenta;
import com.panaderia.panaderia.repository.DetalleVentaRepository;
import com.panaderia.panaderia.service.DetalleVentaService;
import org.springframework.stereotype.Service;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaServiceImpl(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }
    @Override
    public DetalleVenta guardar(
            DetalleVenta detalleVenta) {
        return detalleVentaRepository
                .save(detalleVenta);
    }
}