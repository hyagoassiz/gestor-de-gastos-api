package com.gestor_de_gastos.gestor_de_gastos_api.repository;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Conta;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoConta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("""
            SELECT c
            FROM Conta c
            WHERE c.usuario.id = :usuarioId
              AND (:ativo IS NULL OR c.ativo = :ativo)
              AND (:incluirEmSomas IS NULL OR c.incluirEmSomas = :incluirEmSomas)
              AND (:tipoConta IS NULL OR c.tipoConta = :tipoConta)
              AND (
                   :textoBusca IS NULL OR :textoBusca = '' OR
                   LOWER(c.nome) LIKE LOWER(CONCAT('%', :textoBusca, '%')) OR
                   LOWER(c.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
              )
            """)
    List<Conta> findByFiltro(
            @Param("usuarioId") Long usuarioId,
            @Param("ativo") Boolean ativo,
            @Param("incluirEmSomas") Boolean incluirEmSomas,
            @Param("tipoConta") TipoConta tipoConta,
            @Param("textoBusca") String textoBusca
    );

    @Query("""
            SELECT c
            FROM Conta c
            WHERE c.usuario.id = :usuarioId
              AND (:ativo IS NULL OR c.ativo = :ativo)
              AND (:incluirEmSomas IS NULL OR c.incluirEmSomas = :incluirEmSomas)
              AND (:tipoConta IS NULL OR c.tipoConta = :tipoConta)
              AND (
                   :textoBusca IS NULL OR :textoBusca = '' OR
                   LOWER(c.nome) LIKE LOWER(CONCAT('%', :textoBusca, '%')) OR
                   LOWER(c.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
              )
            """)
    Page<Conta> findByFiltroPaginado(
            @Param("usuarioId") Long usuarioId,
            @Param("ativo") Boolean ativo,
            @Param("incluirEmSomas") Boolean incluirEmSomas,
            @Param("tipoConta") TipoConta tipoConta,
            @Param("textoBusca") String textoBusca,
            Pageable pageable
    );


    Optional<Conta> findByIdAndUsuario(Long id, Usuario usuario);
}
