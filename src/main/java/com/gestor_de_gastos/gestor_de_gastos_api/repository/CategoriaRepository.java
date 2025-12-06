package com.gestor_de_gastos.gestor_de_gastos_api.repository;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

  @Query("""
      SELECT c
      FROM Categoria c
      WHERE c.usuario.id = :usuarioId
        AND (:ativo IS NULL OR c.ativo = :ativo)
        AND (:tipoMovimentacao IS NULL OR c.tipoMovimentacao = :tipoMovimentacao)
        AND (:padrao IS NULL OR c.padrao = :padrao)
        AND (
             :textoBusca IS NULL OR :textoBusca = '' OR
             LOWER(c.nome) LIKE LOWER(CONCAT('%', :textoBusca, '%')) OR
             LOWER(c.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
        )
      ORDER BY c.nome ASC, c.dataHoraCriacao DESC
      """)
  List<Categoria> findByFiltro(
      @Param("usuarioId") Long usuarioId,
      @Param("ativo") Boolean ativo,
      @Param("tipoMovimentacao") TipoMovimentacao tipoMovimentacao,
      @Param("padrao") Boolean padrao,
      @Param("textoBusca") String textoBusca);

  @Query("""
      SELECT c
      FROM Categoria c
      WHERE c.usuario.id = :usuarioId
        AND (:ativo IS NULL OR c.ativo = :ativo)
        AND (:tipoMovimentacao IS NULL OR c.tipoMovimentacao = :tipoMovimentacao)
        AND (:padrao IS NULL OR c.padrao = :padrao)
        AND (
             :textoBusca IS NULL OR :textoBusca = '' OR
             LOWER(c.nome) LIKE LOWER(CONCAT('%', :textoBusca, '%')) OR
             LOWER(c.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
        )
      ORDER BY c.nome ASC, c.dataHoraCriacao DESC
      """)
  Page<Categoria> findByFiltroPaginado(
      @Param("usuarioId") Long usuarioId,
      @Param("ativo") Boolean ativo,
      @Param("tipoMovimentacao") TipoMovimentacao tipoMovimentacao,
      @Param("padrao") Boolean padrao,
      @Param("textoBusca") String textoBusca,
      Pageable pageable);

  Optional<Categoria> findByIdAndUsuario(Long id, Usuario usuario);

  Optional<Categoria> findByUsuarioAndPadraoTrueAndNomeAndTipoMovimentacao(
      Usuario usuario,
      String nome,
      TipoMovimentacao tipoMovimentacao);

  boolean existsByNomeAndTipoMovimentacaoAndUsuario(
      String nome,
      TipoMovimentacao tipoMovimentacao,
      Usuario usuario);

}
