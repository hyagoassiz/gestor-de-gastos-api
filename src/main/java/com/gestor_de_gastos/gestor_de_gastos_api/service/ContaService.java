package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.ContaSaldoDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Conta;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Transacao;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.Situacao;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoConta;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.CategoriaRepository;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.ContaRepository;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioLogadoService usuarioLogadoService;
    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;

    public ContaService(ContaRepository contaRepository, UsuarioLogadoService usuarioLogadoService,
            TransacaoRepository transacaoRepository, CategoriaRepository categoriaRepository) {
        this.contaRepository = contaRepository;
        this.usuarioLogadoService = usuarioLogadoService;
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
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

    public ContaSaldoDTO buscarSaldoPorContaId(Boolean ativo, Long ContaId) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return transacaoRepository.buscarSaldoPorContaId(usuario, ativo, ContaId);
    }

    public void transferirSaldo(Long contaOrigemId, Long contaDestinoId, BigDecimal valor) {
        if (contaOrigemId.equals(contaDestinoId)) {
            throw new IllegalArgumentException("A conta de origem e destino devem ser diferentes");
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero");
        }

        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Conta contaOrigem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada"));
        Conta contaDestino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada"));

        BigDecimal saldoOrigem = transacaoRepository.buscarSaldoPorContaId(usuario, true, contaOrigemId).getSaldo();

        if (saldoOrigem.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente na conta de origem");
        }

        Date dataAtual = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Categoria categoriaSaida = categoriaRepository.findByUsuarioAndPadraoTrueAndNomeAndTipoMovimentacao(
                usuario, "Transferência entre Contas", TipoMovimentacao.SAIDA).orElseThrow(
                        () -> new IllegalArgumentException("Categoria padrão de transferência (SAÍDA) não encontrada"));

        Categoria categoriaEntrada = categoriaRepository.findByUsuarioAndPadraoTrueAndNomeAndTipoMovimentacao(
                usuario, "Transferência entre Contas", TipoMovimentacao.ENTRADA)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Categoria padrão de transferência (ENTRADA) não encontrada"));

        Transacao transacaoSaida = Transacao.builder()
                .situacao(Situacao.PAGO)
                .data(dataAtual)
                .valor(valor)
                .tipoMovimentacao(TipoMovimentacao.SAIDA)
                .conta(contaOrigem)
                .observacao("Transferência para " + contaDestino.getNome())
                .usuario(usuario)
                .categoria(categoriaSaida)
                .geradaAutomaticamente(true)
                .build();

        Transacao transacaoEntrada = Transacao.builder()
                .situacao(Situacao.RECEBIDO)
                .data(dataAtual)
                .valor(valor)
                .tipoMovimentacao(TipoMovimentacao.ENTRADA)
                .conta(contaDestino)
                .observacao("Transferência de " + contaOrigem.getNome())
                .usuario(usuario)
                .categoria(categoriaEntrada)
                .geradaAutomaticamente(true)
                .build();

        transacaoRepository.save(transacaoSaida);
        transacaoRepository.save(transacaoEntrada);
    }

}
