package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Transacao;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.Situacao;
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

    public List<Transacao> listarTodosByFiltro(Situacao situacao,
            TipoMovimentacao tipoMovimentacao,
            String textoBusca) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();
        return transacaoRepository.findByFiltro(usuarioId, situacao, tipoMovimentacao, textoBusca);
    }

    public Page<Transacao> listarPaginadoByFiltro(Situacao situacao,
            TipoMovimentacao tipoMovimentacao,
            String textoBusca, Pageable pageable) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();
        return transacaoRepository.findByFiltroPaginado(usuarioId, situacao, tipoMovimentacao, textoBusca, pageable);
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
        transacaoExistente.setSituacao(transacao.getSituacao());

        return transacaoRepository.save(transacaoExistente);
    }

    public Transacao atualizarPago(Long id, Situacao situacao) {
        Transacao transacaoExistente = buscarPorId(id);
        transacaoExistente.setSituacao(situacao);
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

        if (transacao.getSituacao() == null) {
            throw new IllegalArgumentException("A situação da transação é obrigatória");
        }

        switch (transacao.getTipoMovimentacao()) {
            case ENTRADA -> {
                if (transacao.getSituacao() != Situacao.RECEBIDO &&
                        transacao.getSituacao() != Situacao.NAO_RECEBIDO) {
                    throw new IllegalArgumentException(
                            "Para entradas, a situação só pode ser RECEBIDO ou NAO_RECEBIDO");
                }
            }
            case SAIDA -> {
                if (transacao.getSituacao() != Situacao.PAGO &&
                        transacao.getSituacao() != Situacao.NAO_PAGO) {
                    throw new IllegalArgumentException("Para saídas, a situação só pode ser PAGO ou NAO_PAGO");
                }
            }
            default -> throw new IllegalArgumentException("Tipo de movimentação inválido");
        }
    }

}
