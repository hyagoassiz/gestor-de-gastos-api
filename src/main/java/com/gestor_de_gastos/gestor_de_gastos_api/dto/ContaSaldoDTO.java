package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContaSaldoDTO {

    private Long contaId;
    private String nome;
    private String agencia;
    private String conta;
    private BigDecimal saldo;

}
