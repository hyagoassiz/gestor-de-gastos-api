package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final UsuarioLogadoService usuarioLogadoService;

    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioLogadoService usuarioLogadoService) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }


    public List<Categoria> listarTodos() {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return categoriaRepository.findByUsuario(usuario);
    }

    public Page<Categoria> listarPaginado(Pageable pageable) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return categoriaRepository.findByUsuario(usuario, pageable);
    }

    public List<Categoria> listarPorAtivo(boolean ativo) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return categoriaRepository.findByUsuarioAndAtivo(usuario, ativo);
    }

    public Page<Categoria> listarPaginadoPorAtivo(Pageable pageable, boolean ativo) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return categoriaRepository.findByUsuarioAndAtivo(usuario, ativo, pageable);
    }


    public Categoria buscarPorId(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return categoriaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Categoria salvar(Categoria categoria) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        categoria.setAtivo(true);
        categoria.setUsuario(usuario);

        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        Categoria categoriaExistente = buscarPorId(id);

        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setObservacao(categoria.getObservacao());
        categoriaExistente.setTipoCategoria(categoria.getTipoCategoria());

        return categoriaRepository.save(categoriaExistente);
    }


    public Categoria atualizarAtivo(Long id, boolean ativo) {
        Categoria categoriaExistente = buscarPorId(id);
        categoriaExistente.setAtivo(ativo);
        return categoriaRepository.save(categoriaExistente);
    }


    public void deletarPorId(Long id) {
        Categoria categoriaExistente = buscarPorId(id);
        categoriaRepository.deleteById(categoriaExistente.getId());
    }
}
