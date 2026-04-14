package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.LoginRequest;
import com.system.fisio.application.dto.LoginResponse;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.usecase.AutenticarUsuarioUseCase;
import com.system.fisio.domain.exception.BusinessException;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;

    public AuthController(AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    @Operation(
            summary = "Autenticar usuário",
            description = "Login.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado",
                            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = autenticarUsuarioUseCase.execute(request);
            return ResponseEntity.ok(response);

        } catch (BusinessException ex) {
            ErrorResponse error = new ErrorResponse(
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity
                    .status(400)
                    .body(error);

        } catch (Exception ex) {
            ErrorResponse error = new ErrorResponse(
                    "Erro interno do servidor",
                    LocalDateTime.now()
            );
            return ResponseEntity
                    .status(500)
                    .body(error);
        }
    }
}
