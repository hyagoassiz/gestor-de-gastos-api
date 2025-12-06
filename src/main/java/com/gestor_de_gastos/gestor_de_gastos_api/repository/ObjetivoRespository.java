package com.gestor_de_gastos.gestor_de_gastos_api.repository;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Objetivo;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjetivoRespository extends JpaRepository<Objetivo, Long> {

  @Query("""
      SELECT o
      FROM Objetivo o
      WHERE o.usuario.id = :usuarioId
        AND (
             :textoBusca IS NULL OR :textoBusca = '' OR
             LOWER(o.observacao) LIKE LOWER(CONCAT('%', :textoBusca, '%'))
        )
      ORDER BY o.nome ASC, o.dataHoraCriacao DESC
      """)
  List<Objetivo> findByFiltro(
      @Param("usuarioId") Long usuarioId,
      @Param("textoBusca") String textoBusca);

  Optional<Objetivo> findByIdAndUsuario(Long id, Usuario usuario);

}
