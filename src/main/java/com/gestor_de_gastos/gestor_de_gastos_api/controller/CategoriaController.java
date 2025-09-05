package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
import com.gestor_de_gastos.gestor_de_gastos_api.service.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @GetMapping
    public List<Categoria> listarTodos() {
        return categoriaService.listarTodos();
    }

    @GetMapping("/listar-paginado")
    public Page<Categoria> listarPaginado(Pageable pageable) {
        return categoriaService.listarPaginado(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Categoria> buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    public Categoria salvar(@RequestBody Categoria categoria) {
        return categoriaService.salvar(categoria);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {

        return categoriaService.atualizar(id, categoria);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        categoriaService.deletarPorId(id);
    }


}
