package com.gestor_de_gastos.gestor_de_gastos_api.repository;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.ContaSaldoDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Transacao;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    @Query("""
            SELECT t
            FROM Transacao t
            WHERE t.usuario.id = :usuarioId
              AND (:pago IS NULL OR t.pago = :pago)
              AND (:tipoMovimentacao IS NULL OR t.tipoMovimentacao = :tipoMovimentacao)
              AND (
                   :textoBusca IS NULL OR :textoBusca = '' OR
                   LOWER(t.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
              )
            ORDER BY t.data DESC, t.dataHoraCriacao DESC
            """)
    List<Transacao> findByFiltro(
            @Param("usuarioId") Long usuarioId,
            @Param("pago") Boolean pago,
            @Param("tipoMovimentacao") TipoMovimentacao tipoMovimentacao,
            @Param("textoBusca") String textoBusca);

    @Query("""
            SELECT t
            FROM Transacao t
            WHERE t.usuario.id = :usuarioId
              AND (:pago IS NULL OR t.pago = :pago)
              AND (:tipoMovimentacao IS NULL OR t.tipoMovimentacao = :tipoMovimentacao)
              AND (
                   :textoBusca IS NULL OR :textoBusca = '' OR
                   LOWER(t.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
              )
            ORDER BY t.data DESC, t.dataHoraCriacao DESC
            """)
    Page<Transacao> findByFiltroPaginado(
            @Param("usuarioId") Long usuarioId,
            @Param("pago") Boolean pago,
            @Param("tipoMovimentacao") TipoMovimentacao tipoMovimentacao,
            @Param("textoBusca") String textoBusca,
            Pageable pageable);

    Optional<Transacao> findByIdAndUsuario(Long id, Usuario usuario);

    @Query("""
             SELECT new com.gestor_de_gastos.gestor_de_gastos_api.dto.ContaSaldoDTO(
                 t.conta.id,
                 t.conta.nome,
                 t.conta.agencia,
                 t.conta.conta,
                 COALESCE(SUM(
                     CASE
                         WHEN t.tipoMovimentacao = 'ENTRADA' THEN t.valor
                         ELSE -t.valor
                     END
                 ), 0)
             )
             FROM Transacao t
             WHERE t.usuario = :usuario
               AND t.pago = true
               AND (:ativo IS NULL OR t.conta.ativo = :ativo)
             GROUP BY t.conta.id, t.conta.nome, t.conta.agencia, t.conta.conta
             ORDER BY t.conta.nome
            """)
    List<ContaSaldoDTO> buscarSaldosPorConta(
            @Param("usuario") Usuario usuario,
            @Param("ativo") Boolean ativo);

    @Query("""
                SELECT COALESCE(SUM(t.valor), 0)
                FROM Transacao t
                WHERE t.tipoMovimentacao = 'ENTRADA'
                  AND t.pago = true
                  AND t.usuario.id = :usuarioId
            """)
    BigDecimal getTotalEntradas(@Param("usuarioId") Long usuarioId);

    @Query("""
                SELECT COALESCE(SUM(t.valor), 0)
                FROM Transacao t
                WHERE t.tipoMovimentacao = 'SAIDA'
                  AND t.pago = true
                  AND t.usuario.id = :usuarioId
            """)
    BigDecimal getTotalSaidas(@Param("usuarioId") Long usuarioId);

    @Query("""
                SELECT COALESCE(SUM(t.valor), 0)
                FROM Transacao t
                WHERE t.tipoMovimentacao = 'ENTRADA'
                  AND t.pago = false
                  AND t.usuario.id = :usuarioId
            """)
    BigDecimal getTotalAReceber(@Param("usuarioId") Long usuarioId);

    @Query("""
                SELECT COALESCE(SUM(t.valor), 0)
                FROM Transacao t
                WHERE t.tipoMovimentacao = 'SAIDA'
                  AND t.pago = false
                  AND t.usuario.id = :usuarioId
            """)
    BigDecimal getTotalAPagar(@Param("usuarioId") Long usuarioId);


}
