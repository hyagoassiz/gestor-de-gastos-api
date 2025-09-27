package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Conta;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoConta;
import com.gestor_de_gastos.gestor_de_gastos_api.service.ContaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    public List<Conta> listarTodos(@RequestParam(required = false) Boolean ativo,
                                   @RequestParam(required = false) Boolean incluirEmSomas,
                                   @RequestParam(required = false) TipoConta tipoConta,
                                   @RequestParam(required = false) String textoBusca) {
        return contaService.listarTodosByFiltro(ativo, incluirEmSomas, tipoConta, textoBusca);
    }

    @GetMapping("/listar-paginado")
    public Page<Conta> listarPaginado(Pageable pageable, @RequestParam(required = false) Boolean ativo,
                                      @RequestParam(required = false) Boolean incluirEmSomas,
                                      @RequestParam(required = false) TipoConta tipoConta,
                                      @RequestParam(required = false) String textoBusca) {
        return contaService.listarPaginadoByFiltroPaginado(pageable, ativo, incluirEmSomas, tipoConta, textoBusca);
    }


    @GetMapping("/{id}")
    public Conta buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id);
    }

    @PostMapping
    public Conta salvarOuAtualizar(@RequestBody Conta conta) {
        if (conta.getId() != null) {
            return atualizar(conta.getId(), conta);
        }
        return contaService.salvar(conta);
    }

    @PutMapping
    public Conta atualizar(@PathVariable Long id, @RequestBody Conta conta) {

        return contaService.atualizar(id, conta);
    }

    @PatchMapping("/{id}")
    public Conta atualizarAtivo(@PathVariable Long id, @RequestParam boolean ativo) {
        return contaService.atualizarAtivo(id, ativo);
    }

}
