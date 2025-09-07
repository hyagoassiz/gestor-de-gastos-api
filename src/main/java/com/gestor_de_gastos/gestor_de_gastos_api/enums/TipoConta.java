package com.gestor_de_gastos.gestor_de_gastos_api.enums;

public enum TipoConta {

    CONTA_CORRENTE("Conta Corrente"),
    INVESTIMENTO("Investimento"),
    POUPANCA("Poupança"),
    OUTROS("Outros");

    private final String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
