package com.gestor_de_gastos.gestor_de_gastos_api.model;

public enum TipoAtivo {
    ACAO("Ação"),
    FIIS("Fundo Imobiliário");

    private final String descricao;

    TipoAtivo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
