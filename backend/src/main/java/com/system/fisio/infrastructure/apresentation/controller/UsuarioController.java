package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.usecase.BuscarTodosUsuariosUseCase;
import org.springframework.web.bind.annotation.*;
import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.usecase.CriarUsuarioUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;

    public UsuarioController(
            CriarUsuarioUseCase criarUsuarioUseCase,
            BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase
    ) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.buscarTodosUsuariosUseCase = buscarTodosUsuariosUseCase;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            UsuarioResponse response = criarUsuarioUseCase.execute(usuarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(buscarTodosUsuariosUseCase.execute());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
    
}
