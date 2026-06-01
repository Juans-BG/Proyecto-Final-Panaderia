package com.panaderia.panaderia.controller;

import com.panaderia.panaderia.model.DetalleVenta;
import com.panaderia.panaderia.model.Producto;
import com.panaderia.panaderia.model.Venta;
import com.panaderia.panaderia.service.ClienteService;
import com.panaderia.panaderia.service.DetalleVentaService;
import com.panaderia.panaderia.service.ProductoService;
import com.panaderia.panaderia.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final DetalleVentaService detalleVentaService;

    public VentaController(VentaService ventaService,ClienteService clienteService,ProductoService productoService,DetalleVentaService detalleVentaService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas",ventaService.listarTodas());
        return "ventas";
    }
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model,HttpSession session) {

        model.addAttribute("venta",new Venta());
        model.addAttribute("clientes",clienteService.listarTodos());
        model.addAttribute("productos",productoService.listarTodos());

        List<DetalleVenta> carrito =(List<DetalleVenta>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        model.addAttribute("carrito", carrito);
        Double total = carrito.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
        model.addAttribute("total", total);
        String error = (String) session.getAttribute("error");
        model.addAttribute("error", error);
        session.removeAttribute("error");

        return "formularioVenta";
    }
    @PostMapping("/agregar-producto")
    public String agregarProducto(@RequestParam(required = false) Long productoId, @RequestParam(required = false) Integer cantidad,HttpSession session,Model model) {

        if (productoId == null || cantidad == null) {
            session.setAttribute("error","Debe seleccionar producto y cantidad");

            return "redirect:/ventas/nuevo";
        }

        Producto producto = productoService.buscarPorId(productoId);

        if (producto == null) {
            return "redirect:/ventas/nuevo";
        }
        if (cantidad > producto.getStock()) {
            session.setAttribute("error","Stock insuficiente");
            return "redirect:/ventas/nuevo";
        }
        Double subtotal = producto.getPrecio() * cantidad;
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setSubtotal(subtotal);

        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        carrito.add(detalle);
        session.setAttribute("carrito", carrito);
        return "redirect:/ventas/nuevo";
    }
    @PostMapping("/confirmar")
    public String confirmarVenta(@RequestParam(required = false) Long clienteId,@RequestParam String estado,HttpSession session) {
        List<DetalleVenta> carrito =(List<DetalleVenta>) session.getAttribute("carrito");

        if (clienteId == null) {
            session.setAttribute("error","Debe seleccionar un cliente");
            return "redirect:/ventas/nuevo";
        }

        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/ventas/nuevo";
        }
        Double total = carrito.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();

        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setEstado(estado);
        venta.setTotal(total);
        venta.setCliente(clienteService.buscarPorId(clienteId));
        Venta ventaGuardada = ventaService.guardar(venta);

        for (DetalleVenta detalle : carrito) {
            detalle.setVenta(ventaGuardada);

            detalleVentaService.guardar(detalle);
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoService.guardar(producto);
        }
        session.removeAttribute("carrito");
        return "redirect:/ventas";
    }
    @PostMapping("/guardar")
    public String guardarVenta(@RequestParam Long clienteId,@RequestParam Long productoId,@RequestParam Integer cantidad) {
        Producto producto = productoService.buscarPorId(productoId);
        if (producto == null) {return "redirect:/ventas";
        }
        if (cantidad > producto.getStock()) {
            return "redirect:/ventas";
        }
        Double subtotal = producto.getPrecio() * cantidad;
        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setEstado("PAGADO");
        venta.setTotal(subtotal);
        venta.setCliente(clienteService.buscarPorId(clienteId));
        Venta ventaGuardada = ventaService.guardar(venta);
        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(ventaGuardada);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setSubtotal(subtotal);
        detalleVentaService.guardar(detalle);
        producto.setStock(producto.getStock() - cantidad);
        productoService.guardar(producto);

        return "redirect:/ventas";
    }
}