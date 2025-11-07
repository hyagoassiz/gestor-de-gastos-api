package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardResponseDTO {

    private BigDecimal entradas;
    private BigDecimal saidas;
    private BigDecimal saldo;
    private BigDecimal aReceber;
    private BigDecimal aPagar;

}
