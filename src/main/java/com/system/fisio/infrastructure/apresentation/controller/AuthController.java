package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.LoginRequest;
import com.system.fisio.application.dto.LoginResponse;
import com.system.fisio.application.usecase.AutenticarUsuarioUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;

    public AuthController(AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(autenticarUsuarioUseCase.execute(request));
    }
}
