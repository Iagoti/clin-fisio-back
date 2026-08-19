package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.PermissaoResponse;
import com.system.fisio.application.usecase.BuscarTodasPermissoesUseCase;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Permissões", description = "Catálogo de permissões disponíveis para compor perfis (roles)")
@RestController
@RequestMapping("/permissao")
@SecurityRequirement(name = "bearerAuth")
public class PermissaoController {

    private final BuscarTodasPermissoesUseCase buscarTodasPermissoesUseCase;

    public PermissaoController(BuscarTodasPermissoesUseCase buscarTodasPermissoesUseCase) {
        this.buscarTodasPermissoesUseCase = buscarTodasPermissoesUseCase;
    }

    @Operation(
            summary = "Listar permissões",
            description = "Retorna o catálogo de permissões disponíveis, para uso na tela de gestão de perfis.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = PermissaoResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content)
            }
    )
    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSAO_LISTAR')")
    public ResponseEntity<?> findAll() {
        try {
            List<PermissaoResponse> response = buscarTodasPermissoesUseCase.execute();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    private ResponseEntity<ErrorResponse> internalError(Exception ex) {
        return ResponseEntity.status(500).body(new ErrorResponse("Erro interno do servidor", LocalDateTime.now()));
    }
}
