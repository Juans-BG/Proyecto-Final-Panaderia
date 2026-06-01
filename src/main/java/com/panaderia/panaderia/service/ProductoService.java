package com.panaderia.panaderia.service;

import com.panaderia.panaderia.model.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> listarTodos();

    Producto guardar(Producto producto);

    Producto buscarPorId(Long id);

    void eliminar(Long id);
}