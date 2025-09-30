package com.gestor_de_gastos.gestor_de_gastos_api.repository;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Transacao;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
  @Query("""
      SELECT t
      FROM Transacao t
      WHERE t.usuario.id = :usuarioId
        AND (:pago IS NULL OR t.pago = :pago)
        AND (
             :textoBusca IS NULL OR :textoBusca = '' OR
             LOWER(t.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
        )
      """)
  List<Transacao> findByFiltro(
      @Param("usuarioId") Long usuarioId,
      @Param("pago") Boolean pago,
      @Param("textoBusca") String textoBusca);

  @Query("""
      SELECT t
      FROM Transacao t
      WHERE t.usuario.id = :usuarioId
        AND (:pago IS NULL OR t.pago = :pago)
        AND (
             :textoBusca IS NULL OR :textoBusca = '' OR
             LOWER(t.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
        )
      """)
  Page<Transacao> findByFiltroPaginado(
      @Param("usuarioId") Long usuarioId,
      @Param("pago") Boolean pago,
      @Param("textoBusca") String textoBusca,
      Pageable pageable);

  Optional<Transacao> findByIdAndUsuario(Long id, Usuario usuario);
}
