package com.gestor_de_gastos.gestor_de_gastos_api.repository;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Transacao;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByUsuario(Usuario usuario);

    Page<Transacao> findByUsuario(Usuario usuario, Pageable pageable);

    List<Transacao> findByUsuarioAndPago(Usuario usuario, boolean pago);

    Page<Transacao> findByUsuarioAndPago(Usuario usuario, boolean pago, Pageable pageable);

    Optional<Transacao> findByIdAndUsuario(Long id, Usuario usuario);
}
