package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ObjetivoResponseDTO {

    private Long id;
    private String nome;
    private BigDecimal valorEsperado;
    private BigDecimal valorAtual;
    private BigDecimal percentual;
    private LocalDate dataConclusao;

}
