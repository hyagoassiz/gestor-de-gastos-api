package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AjustarSaldoRequestDTO {
    private Long contaId;
    private BigDecimal valor;

}
