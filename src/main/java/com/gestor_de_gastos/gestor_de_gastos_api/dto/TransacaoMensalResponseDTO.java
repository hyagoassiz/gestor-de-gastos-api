package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import lombok.Getter;

@Getter
public class TransacaoMensalResponseDTO {

    private final String mesAno;
    private final BigDecimal totalEntradas;
    private final BigDecimal totalSaidas;
    private final BigDecimal saldo;

    public TransacaoMensalResponseDTO(
            Integer ano,
            Integer mes,
            BigDecimal totalEntradas,
            BigDecimal totalSaidas,
            BigDecimal saldo) {
        this.totalEntradas = totalEntradas;
        this.totalSaidas = totalSaidas;
        this.saldo = saldo;

        var yearMonth = YearMonth.of(ano, mes);
        var mesAbreviado = yearMonth.getMonth()
                .getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"))
                .replace(".", "");
        this.mesAno = capitalize(mesAbreviado) + "/" + ano;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty())
            return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}