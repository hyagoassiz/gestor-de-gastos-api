package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Conta;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.ContaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository categoriaRepository) {
        this.contaRepository = categoriaRepository;
    }


    public List<Conta> listarTodos() {
        return contaRepository.findAll();
    }

    public Page<Conta> listarPaginado(Pageable pageable) {
        return contaRepository.findAll(pageable);
    }

    public List<Conta> listarPorAtivo(boolean ativo) {
        return contaRepository.findByAtivo(ativo);
    }

    public Page<Conta> listarPaginadoPorAtivo(Pageable pageable, boolean ativo) {
        return contaRepository.findByAtivo(ativo, pageable);
    }


    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.findById(id);
    }

    public Conta salvar(Conta conta) {
        conta.setAtivo(true);
        return contaRepository.save(conta);
    }

    public Conta atualizar(Long id, Conta conta) {
        Conta contaExistente = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com id " + id));

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
        Conta contaExistente = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com id " + id));

        contaExistente.setAtivo(ativo);
        return contaRepository.save(contaExistente);
    }


    public void deletarPorId(Long id) {
        contaRepository.deleteById(id);
    }
}
