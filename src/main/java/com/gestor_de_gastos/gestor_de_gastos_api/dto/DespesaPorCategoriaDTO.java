package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import java.math.BigDecimal;

public class DespesaPorCategoriaDTO {

    private Long categoriaId;
    private String categoria;
    private BigDecimal total;

    public DespesaPorCategoriaDTO(Long categoriaId, String categoria, BigDecimal total) {
        this.categoriaId = categoriaId;
        this.categoria = categoria;
        this.total = total;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getTotal() {
        return total;
    }

}
