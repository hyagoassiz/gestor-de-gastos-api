package com.gestor_de_gastos.gestor_de_gastos_api.enums;

public enum TipoConta {

    CONTA_CORRENTE("Conta Corrente"),
    DINHEIRO("Dinheiro"),
    INVESTIMENTO("Investimento"),
    POUPANCA("Poupança"),
    VR_VA("VR/VA"),
    OUTROS("Outros");

    private final String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
