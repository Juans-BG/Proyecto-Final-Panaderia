package com.panaderia.panaderia.controller;

import com.panaderia.panaderia.model.Producto;
import com.panaderia.panaderia.service.CategoriaService;
import com.panaderia.panaderia.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService,CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos",productoService.listarTodos());
        return "productos";
    }
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto",new Producto());
        model.addAttribute("categorias",categoriaService.listarTodas());
        return "formularioProducto";
    }
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto,BindingResult result,Model model,RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("categorias",categoriaService.listarTodas());
            return "formularioProducto";
        }
        productoService.guardar(producto);
        redirectAttributes.addFlashAttribute("success","Producto guardado correctamente");
        return "redirect:/productos";
    }
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id,Model model) {
        Producto producto = productoService.buscarPorId(id);
        model.addAttribute("producto",producto);
        model.addAttribute("categorias",categoriaService.listarTodas());
        return "formularioProducto";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id,RedirectAttributes redirectAttributes) {

        productoService.eliminar(id);
        redirectAttributes.addFlashAttribute("success","Producto eliminado correctamente");
        return "redirect:/productos";
    }

}