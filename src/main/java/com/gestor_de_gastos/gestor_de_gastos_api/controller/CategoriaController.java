package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
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

    @GetMapping("/listar-todos")
    public List<Categoria> listarTodos(@RequestParam(required = false) Boolean ativo) {
        if (ativo == null) {
            return categoriaService.listarTodos();
        }
        return categoriaService.listarPorAtivo(ativo);
    }

    @GetMapping("/listar-paginado")
    public Page<Categoria> listarPaginado(Pageable pageable, @RequestParam(required = false) Boolean ativo) {
        if (ativo == null) {
            return categoriaService.listarPaginado(pageable);
        }
        return categoriaService.listarPaginadoPorAtivo(pageable, ativo);
    }


    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping("/salvar")
    public Categoria salvarOuAtualizar(@RequestBody Categoria categoria) {
        if (categoria.getId() != null) {
            return atualizar(categoria.getId(), categoria);
        }
        return categoriaService.salvar(categoria);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {

        return categoriaService.atualizar(id, categoria);
    }

    @PatchMapping("/{id}")
    public Categoria atualizarAtivo(@PathVariable Long id, @RequestParam boolean ativo) {
        return categoriaService.atualizarAtivo(id, ativo);
    }


}
