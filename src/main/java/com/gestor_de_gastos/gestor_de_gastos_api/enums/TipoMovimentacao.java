package com.gestor_de_gastos.gestor_de_gastos_api.enums;

public enum TipoMovimentacao {

    ENTRADA("Entrada"),
    SAIDA("Saída");

    private final String descricao;

    TipoMovimentacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
