package com.fiap.fintech.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findAllByAtivoTrue();

    UserDetails findByLoginOrEmail(String login, String email);
}
