package com.panaderia.panaderia.service.impl;

import com.panaderia.panaderia.model.Producto;
import com.panaderia.panaderia.repository.ProductoRepository;
import com.panaderia.panaderia.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }
    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }
    @Override
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}