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
              AND (
                   :textoBusca IS NULL OR :textoBusca = '' OR
                   LOWER(c.nome) LIKE LOWER(CONCAT('%', :textoBusca, '%')) OR
                   LOWER(c.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
              )
            """)
    List<Categoria> findByFiltro(
            @Param("usuarioId") Long usuarioId,
            @Param("ativo") Boolean ativo,
            @Param("tipoMovimentacao") TipoMovimentacao tipoMovimentacao,
            @Param("textoBusca") String textoBusca
    );

    @Query("""
            SELECT c
            FROM Categoria c
            WHERE c.usuario.id = :usuarioId
              AND (:ativo IS NULL OR c.ativo = :ativo)
              AND (:tipoMovimentacao IS NULL OR c.tipoMovimentacao = :tipoMovimentacao)
              AND (
                   :textoBusca IS NULL OR :textoBusca = '' OR
                   LOWER(c.nome) LIKE LOWER(CONCAT('%', :textoBusca, '%')) OR
                   LOWER(c.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
              )
            """)
    Page<Categoria> findByFiltroPaginado(
            @Param("usuarioId") Long usuarioId,
            @Param("ativo") Boolean ativo,
            @Param("tipoMovimentacao") TipoMovimentacao tipoMovimentacao,
            @Param("textoBusca") String textoBusca,
            Pageable pageable
    );

    Optional<Categoria> findByIdAndUsuario(Long id, Usuario usuario);

}
