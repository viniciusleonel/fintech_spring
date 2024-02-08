package com.fiap.fintech.controller;


import com.fiap.fintech.domain.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Stream;

@RestController
@RequestMapping("usuarios")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = {"http://127.0.0.1:5500/", "https://projeto-fintech-p5xm.vercel.app/", "https://jsfiddle.net/"})
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        var usuario = new Usuario(dados, passwordEncoder);
        repository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    @GetMapping("listar")
    public ResponseEntity<Stream<DadosDetalhamentoUsuario>> listar() {
        var usuarios = repository.findAllByAtivoTrue().stream().map(DadosDetalhamentoUsuario::new);
        return ResponseEntity.ok(usuarios);
    }



    @GetMapping("detalhar/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var usuario = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @PutMapping("atualizar")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados) {
        var usuario = repository.getReferenceById(dados.id());
        usuario.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("deletar/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        usuario.excluir(usuario);

        return ResponseEntity.noContent().build();
    }


}
