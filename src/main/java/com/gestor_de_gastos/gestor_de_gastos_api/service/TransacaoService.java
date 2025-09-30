package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Transacao;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    private final UsuarioLogadoService usuarioLogadoService;

    public TransacaoService(TransacaoRepository transacaoRepository, UsuarioLogadoService usuarioLogadoService) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    public List<Transacao> listarTodosByFiltro(Boolean pago,
            String textoBusca) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();
        return transacaoRepository.findByFiltro(usuarioId, pago, textoBusca);
    }

    public Page<Transacao> listarPaginadoByFiltro(Boolean pago,
            String textoBusca, Pageable pageable) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();
        return transacaoRepository.findByFiltroPaginado(usuarioId, pago, textoBusca, pageable);
    }

    public Transacao buscarPorId(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return transacaoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }

    public Transacao salvar(Transacao transacao) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        transacao.setUsuario(usuario);

        return transacaoRepository.save(transacao);
    }

    public Transacao atualizar(Long id, Transacao transacao) {
        Transacao transacaoExistente = buscarPorId(id);

        transacaoExistente.setData(transacao.getData());
        transacaoExistente.setValor(transacao.getValor());
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
}
