package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.LoginRequestDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.dto.LoginResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.dto.UsuarioRequestDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.dto.UsuarioResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return usuarioService.autenticar(dto);
    }

    @PostMapping("/cadastrar")
    public UsuarioResponseDTO registrar(@RequestBody UsuarioRequestDTO dto) {
        return usuarioService.registrar(dto);
    }
}
