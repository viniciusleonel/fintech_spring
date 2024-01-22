package com.fiap.fintech.domain.usuario;

public record DadosDetalhamentoUsuario(String login, String email) {

    public DadosDetalhamentoUsuario(Usuario usuario){
        this(usuario.getLogin(), usuario.getEmail());
    }
}
