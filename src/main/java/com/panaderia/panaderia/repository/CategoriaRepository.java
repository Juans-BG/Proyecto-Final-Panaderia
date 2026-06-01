package com.panaderia.panaderia.repository;

import com.panaderia.panaderia.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}