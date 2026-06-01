package com.panaderia.panaderia.controller;

import com.panaderia.panaderia.model.Cliente;
import com.panaderia.panaderia.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(
            ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute(
                "clientes",
                clienteService.listarTodos());
        return "clientes";
    }
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute(
                "cliente",
                new Cliente());
        return "formularioCliente";
    }
    @PostMapping("/guardar")
    public String guardarCliente(
            @Valid @ModelAttribute Cliente cliente,
            BindingResult result) {
        if (result.hasErrors()) {
            return "formularioCliente";
        }
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }
    @GetMapping("/editar/{id}")
    public String editarCliente(
            @PathVariable Long id,
            Model model) {
        Cliente cliente =
                clienteService.buscarPorId(id);
        model.addAttribute(
                "cliente",
                cliente);
        return "formularioCliente";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            clienteService.eliminar(id);
            redirectAttributes.addFlashAttribute(
                    "mensajeExito",
                    "Cliente eliminado correctamente.");
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    "No se puede eliminar el cliente porque tiene ventas asociadas.");
        }
        return "redirect:/clientes";
    }
}