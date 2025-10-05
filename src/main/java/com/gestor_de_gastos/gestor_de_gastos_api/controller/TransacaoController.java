package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Transacao;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;
import com.gestor_de_gastos.gestor_de_gastos_api.service.TransacaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public List<Transacao> listarTodos(
            @RequestParam(required = false) Boolean pago,
            @RequestParam(required = false) TipoMovimentacao tipoMovimentacao,
            @RequestParam(required = false) String textoBusca) {
        return transacaoService.listarTodosByFiltro(
                pago,
                tipoMovimentacao,
                textoBusca);
    }

    @GetMapping("/listar-paginado")
    public Page<Transacao> listarPaginado(
            @RequestParam(required = false) Boolean pago,
            @RequestParam(required = false) TipoMovimentacao tipoMovimentacao,
            @RequestParam(required = false) String textoBusca,
            Pageable pageable) {
        return transacaoService.listarPaginadoByFiltro(
                pago,
                tipoMovimentacao,
                textoBusca,
                pageable);
    }

    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable Long id) {
        return transacaoService.buscarPorId(id);
    }

    @PostMapping
    public Transacao salvarOuAtualizar(@RequestBody Transacao transacao) {
        if (transacao.getId() != null) {
            return atualizar(transacao.getId(), transacao);
        }
        return transacaoService.salvar(transacao);
    }

    @PutMapping("/{id}")
    public Transacao atualizar(@PathVariable Long id, @RequestBody Transacao transacao) {

        return transacaoService.atualizar(id, transacao);
    }

    @PatchMapping("/{id}")
    public Transacao atualizarPago(@PathVariable Long id, @RequestParam boolean pago) {
        return transacaoService.atualizarPago(id, pago);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        transacaoService.deletarPorId(id);
    }
}
