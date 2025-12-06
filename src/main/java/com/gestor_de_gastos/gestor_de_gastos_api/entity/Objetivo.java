package com.gestor_de_gastos.gestor_de_gastos_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "objetivo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Objetivo extends BaseEntity {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O campo valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "O campo dataConclusao é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataConclusao;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;
}
