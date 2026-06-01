package com.panaderia.panaderia.service;

import com.panaderia.panaderia.model.Venta;

import java.util.List;

public interface VentaService {

    List<Venta> listarTodas();

    Venta guardar(Venta venta);

}