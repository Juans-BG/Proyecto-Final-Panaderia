package com.panaderia.panaderia.service.impl;

import com.panaderia.panaderia.model.Categoria;
import com.panaderia.panaderia.repository.CategoriaRepository;
import com.panaderia.panaderia.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }
    @Override
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    @Override
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }
    @Override
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}