package com.gestor_de_gastos.gestor_de_gastos_api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataHoraCriacao;

    @UpdateTimestamp
    private LocalDateTime dataHoraAtualizacao;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataCriacao() {
        return dataHoraCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataHoraAtualizacao;
    }

}
