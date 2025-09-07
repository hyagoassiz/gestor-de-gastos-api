package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public Page<Categoria> listarPaginado(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    public List<Categoria> listarPorAtivo(boolean ativo) {
        return categoriaRepository.findByAtivo(ativo);
    }

    public Page<Categoria> listarPaginadoPorAtivo(Pageable pageable, boolean ativo) {
        return categoriaRepository.findByAtivo(ativo, pageable);
    }


    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria salvar(Categoria categoria) {
        categoria.setAtivo(true);
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        Categoria categoriaExistente = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id " + id));

        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setObservacao(categoria.getObservacao());
        categoriaExistente.setTipoCategoria(categoria.getTipoCategoria());

        return categoriaRepository.save(categoriaExistente);
    }

    public Categoria atualizarAtivo(Long id, boolean ativo) {
        Categoria categoriaExistente = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id " + id));

        categoriaExistente.setAtivo(ativo);
        return categoriaRepository.save(categoriaExistente);
    }


    public void deletarPorId(Long id) {
        categoriaRepository.deleteById(id);
    }
}
