package com.gestor_de_gastos.gestor_de_gastos_api.repository;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Conta;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByUsuario(Usuario usuario);

    Page<Conta> findByUsuario(Usuario usuario, Pageable pageable);

    List<Conta> findByUsuarioAndAtivo(Usuario usuario, boolean ativo);

    Page<Conta> findByUsuarioAndAtivo(Usuario usuario, boolean ativo, Pageable pageable);

    Optional<Conta> findByIdAndUsuario(Long id, Usuario usuario);
}
