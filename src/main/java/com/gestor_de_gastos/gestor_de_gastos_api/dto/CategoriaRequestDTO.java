package com.gestor_de_gastos.gestor_de_gastos_api.dto;

import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;

public class CategoriaRequestDTO {

    private Long id;
    private String nome;
    private String observacao;
    private Boolean ativo;
    private TipoMovimentacao tipoMovimentacao;

    public CategoriaRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }
}