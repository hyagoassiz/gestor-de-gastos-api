package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.CategoriaRequestDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;
import com.gestor_de_gastos.gestor_de_gastos_api.service.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> listarTodo(@RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) TipoMovimentacao tipoMovimentacao,
            @RequestParam(required = false) Boolean padrao,
            @RequestParam(required = false) String textoBusca) {
        return categoriaService.listarTodosByFiltro(ativo, tipoMovimentacao, padrao, textoBusca);
    }

    @GetMapping("/listar-paginado")
    public Page<Categoria> listarPaginado(Pageable pageable, @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) TipoMovimentacao tipoMovimentacao,
            @RequestParam(required = false) Boolean padrao,
            @RequestParam(required = false) String textoBusca) {
        return categoriaService.listarPaginadoByFiltroPaginado(pageable, ativo, tipoMovimentacao, padrao, textoBusca);
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    public Categoria salvarOuAtualizar(@RequestBody CategoriaRequestDTO categoriaDTO) {
        if (categoriaDTO.getId() != null) {
            return atualizar(categoriaDTO.getId(), categoriaDTO);
        }
        return categoriaService.salvar(categoriaDTO);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaDTO) {

        return categoriaService.atualizar(id, categoriaDTO);
    }

    @PatchMapping("/{id}")
    public Categoria atualizarAtivo(@PathVariable Long id, @RequestParam boolean ativo) {
        return categoriaService.atualizarAtivo(id, ativo);
    }

}
