package com.fiap.fintech.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(

        @NotNull
        Long id,

        String login,

        @Email
        String email,

        String senha
) {
}
