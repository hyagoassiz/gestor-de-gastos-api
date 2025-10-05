package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Transacao;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    private final UsuarioLogadoService usuarioLogadoService;

    private final CategoriaService categoriaService;

    public TransacaoService(TransacaoRepository transacaoRepository,
                            UsuarioLogadoService usuarioLogadoService,
                            CategoriaService categoriaService) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioLogadoService = usuarioLogadoService;
        this.categoriaService = categoriaService;
    }

    public List<Transacao> listarTodosByFiltro(Boolean pago,
                                               TipoMovimentacao tipoMovimentacao,
                                               String textoBusca) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();
        return transacaoRepository.findByFiltro(usuarioId, pago, tipoMovimentacao, textoBusca);
    }

    public Page<Transacao> listarPaginadoByFiltro(Boolean pago,
                                                  TipoMovimentacao tipoMovimentacao,
                                                  String textoBusca, Pageable pageable) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();
        return transacaoRepository.findByFiltroPaginado(usuarioId, pago, tipoMovimentacao, textoBusca, pageable);
    }

    public Transacao buscarPorId(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return transacaoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }

    public Transacao salvar(Transacao transacao) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();

        validarTransacao(transacao);

        transacao.setUsuario(usuario);

        return transacaoRepository.save(transacao);
    }

    public Transacao atualizar(Long id, Transacao transacao) {
        validarTransacao(transacao);

        Transacao transacaoExistente = buscarPorId(id);

        transacaoExistente.setTipoMovimentacao(transacao.getTipoMovimentacao());
        transacaoExistente.setData(transacao.getData());
        transacaoExistente.setValor(transacao.getValor());
        transacaoExistente.setCategoria(transacao.getCategoria());
        transacaoExistente.setConta(transacao.getConta());
        transacaoExistente.setObservacao(transacao.getObservacao());
        transacaoExistente.setPago(transacao.getPago());

        return transacaoRepository.save(transacaoExistente);
    }

    public Transacao atualizarPago(Long id, boolean pago) {
        Transacao transacaoExistente = buscarPorId(id);
        transacaoExistente.setPago(pago);
        return transacaoRepository.save(transacaoExistente);
    }

    public void deletarPorId(Long id) {
        Transacao transacaoExistente = buscarPorId(id);
        transacaoRepository.deleteById(transacaoExistente.getId());
    }

    private void validarTransacao(Transacao transacao) {
        if (transacao.getCategoria() == null || transacao.getCategoria().getId() == null) {
            throw new IllegalArgumentException("A transação deve possuir uma categoria");
        }

        var categoria = categoriaService.buscarPorId(transacao.getCategoria().getId());

        transacao.setCategoria(categoria);

        if (transacao.getTipoMovimentacao() != categoria.getTipoMovimentacao()) {
            throw new IllegalArgumentException("A categoria selecionada não corresponde ao tipo de movimentação");
        }
    }
}

