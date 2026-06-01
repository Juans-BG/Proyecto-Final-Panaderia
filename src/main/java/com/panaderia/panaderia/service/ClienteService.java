package com.panaderia.panaderia.service;

import com.panaderia.panaderia.model.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> listarTodos();

    Cliente guardar(Cliente cliente);

    Cliente buscarPorId(Long id);

    void eliminar(Long id);
}