package com.gestor_de_gastos.gestor_de_gastos_api.enums;

public enum Situacao {

    RECEBIDO("Recebido"),
    NAO_RECEBIDO("Não Recebido"),
    PAGO("Pago"),
    NAO_PAGO("Não Pago");

    private final String descricao;

    Situacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
