package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import java.math.BigDecimal;

public class ContaSaldoDTO {

    private Long contaId;
    private String nome;
    private String agencia;
    private String conta;
    private BigDecimal saldo;

    public ContaSaldoDTO(Long contaId, String nome, String agencia, String conta, BigDecimal saldo) {
        this.contaId = contaId;
        this.nome = nome;
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
    }

    public Long getContaId() {
        return contaId;
    }

    public String getNome() {
        return nome;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
