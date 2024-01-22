package com.fiap.fintech.domain.usuario;

public record DadosDetalhamentoUsuario(Long id, String login, String email, Boolean ativo) {

    public DadosDetalhamentoUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin(), usuario.getEmail(), usuario.getAtivo());
    }
}
