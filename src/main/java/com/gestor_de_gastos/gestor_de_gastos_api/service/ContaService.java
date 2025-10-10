package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.ContaSaldoDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Conta;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoConta;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.ContaRepository;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.TransacaoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioLogadoService usuarioLogadoService;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, UsuarioLogadoService usuarioLogadoService,
            TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.usuarioLogadoService = usuarioLogadoService;
        this.transacaoRepository = transacaoRepository;
    }

    public List<Conta> listarTodosByFiltro(Boolean ativo, Boolean incluirEmSomas, TipoConta tipoConta,
            String textoBusca) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();
        return contaRepository.findByFiltro(usuarioId, ativo, incluirEmSomas, tipoConta, textoBusca);
    }

    public Page<Conta> listarPaginadoByFiltroPaginado(Pageable pageable,
            Boolean ativo,
            Boolean incluirEmSomas,
            TipoConta tipoConta,
            String textoBusca) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();
        return contaRepository.findByFiltroPaginado(usuarioId, ativo, incluirEmSomas, tipoConta, textoBusca, pageable);
    }

    public Conta buscarPorId(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return contaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public Conta salvar(Conta conta) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        conta.setUsuario(usuario);

        return contaRepository.save(conta);
    }

    public Conta atualizar(Long id, Conta conta) {
        Conta contaExistente = buscarPorId(id);

        contaExistente.setNome(conta.getNome());
        contaExistente.setTipoConta(conta.getTipoConta());
        contaExistente.setAgencia(conta.getAgencia());
        contaExistente.setConta(conta.getConta());
        contaExistente.setObservacao(conta.getObservacao());
        contaExistente.setIncluirEmSomas(conta.getIncluirEmSomas());
        contaExistente.setAtivo(conta.getAtivo());

        return contaRepository.save(contaExistente);
    }

    public Conta atualizarAtivo(Long id, boolean ativo) {
        Conta contaExistente = buscarPorId(id);
        contaExistente.setAtivo(ativo);
        return contaRepository.save(contaExistente);
    }

    public List<ContaSaldoDTO> listarSaldosPorUsuario(Boolean ativo) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return transacaoRepository.buscarSaldosPorConta(usuario, ativo);
    }

}
