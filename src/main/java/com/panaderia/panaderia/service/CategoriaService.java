package com.panaderia.panaderia.service;

import com.panaderia.panaderia.model.Categoria;

import java.util.List;

public interface CategoriaService {

    List<Categoria> listarTodas();

    Categoria guardar(Categoria categoria);

    Categoria buscarPorId(Long id);

    void eliminar(Long id);

}