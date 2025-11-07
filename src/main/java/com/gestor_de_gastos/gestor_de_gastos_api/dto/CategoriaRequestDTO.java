package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaRequestDTO {

    private Long id;
    private String nome;
    private String observacao;
    private Boolean ativo;
    private TipoMovimentacao tipoMovimentacao;

}