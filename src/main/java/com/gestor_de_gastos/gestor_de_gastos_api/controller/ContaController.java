package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Conta;
import com.gestor_de_gastos.gestor_de_gastos_api.service.ContaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping("listar-todos")
    public List<Conta> listarTodos(@RequestParam(required = false) Boolean ativo) {
        if (ativo == null) {
            return contaService.listarTodos();
        }
        return contaService.listarPorAtivo(ativo);
    }

    @GetMapping("/listar-paginado")
    public Page<Conta> listarPaginado(Pageable pageable, @RequestParam(required = false) Boolean ativo) {
        if (ativo == null) {
            return contaService.listarPaginado(pageable);
        }
        return contaService.listarPaginadoPorAtivo(pageable, ativo);
    }


    @GetMapping("/{id}")
    public Optional<Conta> buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id);
    }

    @PostMapping("/salvar")
    public Conta salvarOuAtualizar(@RequestBody Conta conta) {
        if (conta.getId() != null) {
            return atualizar(conta.getId(), conta);
        }
        return contaService.salvar(conta);
    }

    @PutMapping("/{id}")
    public Conta atualizar(@PathVariable Long id, @RequestBody Conta conta) {

        return contaService.atualizar(id, conta);
    }

    @PatchMapping("/{id}")
    public Conta atualizarAtivo(@PathVariable Long id, @RequestParam boolean ativo) {
        return contaService.atualizarAtivo(id, ativo);
    }


    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        contaService.deletarPorId(id);
    }
}
