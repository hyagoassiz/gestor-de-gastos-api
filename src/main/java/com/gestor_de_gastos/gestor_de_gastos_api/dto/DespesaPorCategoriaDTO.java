package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DespesaPorCategoriaDTO {

    private Long categoriaId;
    private String categoria;
    private BigDecimal total;

}
