package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.model.Ativo;
import com.gestor_de_gastos.gestor_de_gastos_api.service.AtivoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ativos")
public class AtivoController {

    private final AtivoService service;

    public AtivoController(AtivoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ativo> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Ativo buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Ativo criar(@RequestBody Ativo ativo) {
        ativo.setAtivo(true);
        return service.salvar(ativo);
    }

    @PutMapping("/{id}")
    public Ativo atualizar(@PathVariable Long id, @RequestBody Ativo ativo) {
        ativo.setId(id);
        return service.salvar(ativo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
