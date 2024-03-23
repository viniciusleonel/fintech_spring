package com.fiap.fintech.infra.security;

import com.fiap.fintech.domain.usuario.UsuarioRepository;
import com.fiap.fintech.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    private static final String[] AUTH_WHITELIST = {
            // -- Registration and Login Endpoints --
            "/usuarios/cadastrar",
            "/login"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestURI = request.getRequestURI();

        // Allow registration and login endpoints without token
        if (isAuthWhitelisted(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        var authorizationToken = recuperarToken(request);

        if (authorizationToken != null) {
            var email = tokenService.obterUsuario(authorizationToken);
            var usuario = repository.findByEmail(email);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        filterChain.doFilter(request,response);
    }

    private boolean isAuthWhitelisted(String requestURI) {
        for (String endpoint : AUTH_WHITELIST) {
            if (requestURI.contains(endpoint)) {
                return true;
            }
        }
        return false;
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer", "").trim();
        }

        return null;
    }
}
