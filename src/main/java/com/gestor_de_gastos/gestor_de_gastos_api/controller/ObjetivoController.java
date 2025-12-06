package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.ObjetivoResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Objetivo;
import com.gestor_de_gastos.gestor_de_gastos_api.service.ObjetivoService;

@RestController
@RequestMapping("/objetivos")
public class ObjetivoController {

    private final ObjetivoService objetivoService;

    public ObjetivoController(ObjetivoService objetivoService) {
        this.objetivoService = objetivoService;
    }

    @GetMapping
    public List<ObjetivoResponseDTO> listarTodos(@RequestParam(required = false) String textoBusca) {
        return objetivoService.listarTodosByFiltro(textoBusca);
    }

    @GetMapping("/{id}")
    public Objetivo buscarPorId(@PathVariable Long id) {
        return objetivoService.buscarPorId(id);
    }

    @PostMapping
    public Objetivo salvarOuAtualizar(@RequestBody Objetivo objetivo) {
        if (objetivo.getId() != null) {
            return atualizar(objetivo.getId(), objetivo);
        }

        return objetivoService.salvar(objetivo);
    }

    @PutMapping("/{id}")
    public Objetivo atualizar(@PathVariable Long id, @RequestBody Objetivo objetivo) {

        return objetivoService.atualizar(id, objetivo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        objetivoService.deletarPorId(id);
    }

}
