package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import java.math.BigDecimal;

public class DashboardResponseDTO {

    private BigDecimal entradas;
    private BigDecimal saidas;
    private BigDecimal saldo;
    private BigDecimal aReceber;
    private BigDecimal aPagar;


    public DashboardResponseDTO(
            BigDecimal entradas,
            BigDecimal saidas,
            BigDecimal saldo,
            BigDecimal aReceber,
            BigDecimal aPagar
    ) {
        this.entradas = entradas;
        this.saidas = saidas;
        this.saldo = saldo;
        this.aReceber = aReceber;
        this.aPagar = aPagar;
    }

    public BigDecimal getEntradas() {
        return entradas;
    }

    public BigDecimal getSaidas() {
        return saidas;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getaReceber() {
        return aReceber;
    }

    public BigDecimal getaPagar() {
        return aPagar;
    }

}
