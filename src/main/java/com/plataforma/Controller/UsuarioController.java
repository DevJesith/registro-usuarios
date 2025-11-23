package com.plataforma.controller;

import com.plataforma.model.Usuario;
import com.plataforma.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "index";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            return "index";
        }

        try {
            usuarioService.registrarUsuario(usuario);
            model.addAttribute("mensaje", "¡Registro exitoso! Bienvenido " + usuario.getNombre());
            model.addAttribute("usuario", new Usuario());
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "index";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        return "usuarios";
    }
}