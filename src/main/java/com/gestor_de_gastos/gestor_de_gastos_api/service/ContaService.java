package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Conta;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.ContaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioLogadoService usuarioLogadoService;

    public ContaService(ContaRepository contaRepository, UsuarioLogadoService usuarioLogadoService) {
        this.contaRepository = contaRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }


    public List<Conta> listarTodos() {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return contaRepository.findByUsuario(usuario);
    }

    public Page<Conta> listarPaginado(Pageable pageable) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return contaRepository.findByUsuario(usuario, pageable);
    }

    public List<Conta> listarPorAtivo(boolean ativo) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return contaRepository.findByUsuarioAndAtivo(usuario, ativo);
    }

    public Page<Conta> listarPaginadoPorAtivo(Pageable pageable, boolean ativo) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return contaRepository.findByUsuarioAndAtivo(usuario, ativo, pageable);
    }


    public Conta buscarPorId(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return contaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public Conta salvar(Conta conta) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        conta.setAtivo(true);
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


    public void deletarPorId(Long id) {
        Conta contaExistente = buscarPorId(id);
        contaRepository.deleteById(contaExistente.getId());
    }
}
