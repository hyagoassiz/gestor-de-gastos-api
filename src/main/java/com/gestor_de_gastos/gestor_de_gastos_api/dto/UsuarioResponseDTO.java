package com.gestor_de_gastos.gestor_de_gastos_api.dto;

public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;

    public UsuarioResponseDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
