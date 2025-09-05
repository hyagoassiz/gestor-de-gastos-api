package com.gestor_de_gastos.gestor_de_gastos_api.enums;

public enum TipoCategoria {

    ENTRADA("Entrada"),
    SAIDA("Saída");

    private final String descricao;

    TipoCategoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
