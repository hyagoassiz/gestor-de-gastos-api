package com.gestor_de_gastos.gestor_de_gastos_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoConta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "conta")
public class Conta extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O tipo da conta é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    private String agencia;

    private String conta;

    private String observacao;

    @NotNull(message = "O campo incluirEmSomas é obrigatório")
    private Boolean incluirEmSomas;

    @NotNull(message = "O campo ativo é obrigatório")
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

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

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getIncluirEmSomas() {
        return incluirEmSomas;
    }

    public void setIncluirEmSomas(Boolean incluirEmSomas) {
        this.incluirEmSomas = incluirEmSomas;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
