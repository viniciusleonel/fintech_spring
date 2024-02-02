package com.fiap.fintech.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> tratarErro403(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Credenciais inválidas. Acesso não autorizado.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity tratarErro500(DataIntegrityViolationException ex) {
        // Lógica para tratamento genérico de DataIntegrityViolationException
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro de integridade de dados.");
    }

    private record DadosErroValidacao(String campo, String mensagem) {

        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
