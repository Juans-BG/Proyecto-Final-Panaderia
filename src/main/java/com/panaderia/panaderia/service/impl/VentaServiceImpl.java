package com.panaderia.panaderia.service.impl;

import com.panaderia.panaderia.model.Venta;
import com.panaderia.panaderia.repository.VentaRepository;
import com.panaderia.panaderia.service.VentaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;

    public VentaServiceImpl(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }
    @Override
    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }
    @Override
    public Venta guardar(Venta venta) {
        return ventaRepository.save(venta);
    }
}