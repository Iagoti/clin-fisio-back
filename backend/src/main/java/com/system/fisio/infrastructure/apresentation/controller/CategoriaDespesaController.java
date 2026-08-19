package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.CategoriaDespesaRequest;
import com.system.fisio.application.usecase.AtualizarCategoriaDespesaUseCase;
import com.system.fisio.application.usecase.BuscarTodasCategoriasDespesaUseCase;
import com.system.fisio.application.usecase.CriarCategoriaDespesaUseCase;
import com.system.fisio.domain.exception.BusinessException;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Financeiro - Categorias de despesa", description = "Catálogo de categorias de despesa (contas a pagar)")
@RestController
@RequestMapping("/financeiro/categoria-despesa")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaDespesaController {

    private final CriarCategoriaDespesaUseCase criarCategoriaDespesaUseCase;
    private final AtualizarCategoriaDespesaUseCase atualizarCategoriaDespesaUseCase;
    private final BuscarTodasCategoriasDespesaUseCase buscarTodasCategoriasDespesaUseCase;

    public CategoriaDespesaController(
            CriarCategoriaDespesaUseCase criarCategoriaDespesaUseCase,
            AtualizarCategoriaDespesaUseCase atualizarCategoriaDespesaUseCase,
            BuscarTodasCategoriasDespesaUseCase buscarTodasCategoriasDespesaUseCase
    ) {
        this.criarCategoriaDespesaUseCase = criarCategoriaDespesaUseCase;
        this.atualizarCategoriaDespesaUseCase = atualizarCategoriaDespesaUseCase;
        this.buscarTodasCategoriasDespesaUseCase = buscarTodasCategoriasDespesaUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FINANCEIRO_CATEGORIA_GERENCIAR')")
    public ResponseEntity<?> create(@Valid @RequestBody CategoriaDespesaRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(criarCategoriaDespesaUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FINANCEIRO_CATEGORIA_LISTAR')")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(buscarTodasCategoriasDespesaUseCase.execute());
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('FINANCEIRO_CATEGORIA_GERENCIAR')")
    public ResponseEntity<?> update(@Valid @RequestBody CategoriaDespesaRequest request) {
        try {
            return ResponseEntity.ok(atualizarCategoriaDespesaUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    private ResponseEntity<ErrorResponse> badRequest(BusinessException ex) {
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()));
    }

    private ResponseEntity<ErrorResponse> internalError(Exception ex) {
        return ResponseEntity.status(500).body(new ErrorResponse("Erro interno do servidor", LocalDateTime.now()));
    }
}
