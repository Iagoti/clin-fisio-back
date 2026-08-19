package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.usecase.GerarDashboardFinanceiroUseCase;
import com.system.fisio.application.usecase.GerarFluxoCaixaUseCase;
import com.system.fisio.domain.exception.BusinessException;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Financeiro - Relatórios", description = "Dashboard e fluxo de caixa")
@RestController
@RequestMapping("/financeiro")
@SecurityRequirement(name = "bearerAuth")
public class FinanceiroRelatorioController {

    private final GerarFluxoCaixaUseCase gerarFluxoCaixaUseCase;
    private final GerarDashboardFinanceiroUseCase gerarDashboardFinanceiroUseCase;

    public FinanceiroRelatorioController(
            GerarFluxoCaixaUseCase gerarFluxoCaixaUseCase,
            GerarDashboardFinanceiroUseCase gerarDashboardFinanceiroUseCase
    ) {
        this.gerarFluxoCaixaUseCase = gerarFluxoCaixaUseCase;
        this.gerarDashboardFinanceiroUseCase = gerarDashboardFinanceiroUseCase;
    }

    @GetMapping("/fluxo-caixa")
    @PreAuthorize("hasAuthority('FINANCEIRO_RELATORIO_VISUALIZAR')")
    public ResponseEntity<?> fluxoCaixa(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtFim
    ) {
        try {
            return ResponseEntity.ok(gerarFluxoCaixaUseCase.execute(dtInicio, dtFim));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('FINANCEIRO_RELATORIO_VISUALIZAR')")
    public ResponseEntity<?> dashboard() {
        try {
            return ResponseEntity.ok(gerarDashboardFinanceiroUseCase.execute());
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
