package com.gestor_de_gastos.gestor_de_gastos_api.repository;

import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByUsuario(Usuario usuario);

    Page<Categoria> findByUsuario(Usuario usuario, Pageable pageable);

    List<Categoria> findByUsuarioAndAtivo(Usuario usuario, boolean ativo);

    Page<Categoria> findByUsuarioAndAtivo(Usuario usuario, boolean ativo, Pageable pageable);

    Optional<Categoria> findByIdAndUsuario(Long id, Usuario usuario);

}
