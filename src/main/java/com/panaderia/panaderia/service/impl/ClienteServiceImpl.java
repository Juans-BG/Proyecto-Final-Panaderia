package com.panaderia.panaderia.service.impl;

import com.panaderia.panaderia.model.Cliente;
import com.panaderia.panaderia.repository.ClienteRepository;
import com.panaderia.panaderia.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
    @Override
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}