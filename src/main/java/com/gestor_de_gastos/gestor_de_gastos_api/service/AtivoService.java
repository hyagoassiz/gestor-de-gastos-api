package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.model.Ativo;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.AtivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoService {

    private final AtivoRepository repository;

    public AtivoService(AtivoRepository repository) {
        this.repository = repository;
    }

    public List<Ativo> listarTodos() {
        return repository.findAll();
    }

    public Ativo buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Ativo salvar(Ativo ativo) {
        return repository.save(ativo);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
