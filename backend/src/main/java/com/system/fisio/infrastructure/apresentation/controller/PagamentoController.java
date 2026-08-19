package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.BaixarPagamentoRequest;
import com.system.fisio.application.dto.PagamentoFiltro;
import com.system.fisio.application.dto.PagamentoRequest;
import com.system.fisio.application.usecase.AtualizarPagamentoUseCase;
import com.system.fisio.application.usecase.BaixarPagamentoUseCase;
import com.system.fisio.application.usecase.BuscarPagamentoByIdUseCase;
import com.system.fisio.application.usecase.BuscarTodosPagamentosUseCase;
import com.system.fisio.application.usecase.CancelarPagamentoUseCase;
import com.system.fisio.application.usecase.CriarPagamentoUseCase;
import com.system.fisio.domain.exception.BusinessException;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Financeiro - Contas a receber", description = "Pagamentos de pacientes (pacotes, sessões avulsas de pilates, lançamentos manuais)")
@RestController
@RequestMapping("/financeiro/pagamento")
@SecurityRequirement(name = "bearerAuth")
public class PagamentoController {

    private final CriarPagamentoUseCase criarPagamentoUseCase;
    private final AtualizarPagamentoUseCase atualizarPagamentoUseCase;
    private final BaixarPagamentoUseCase baixarPagamentoUseCase;
    private final CancelarPagamentoUseCase cancelarPagamentoUseCase;
    private final BuscarTodosPagamentosUseCase buscarTodosPagamentosUseCase;
    private final BuscarPagamentoByIdUseCase buscarPagamentoByIdUseCase;

    public PagamentoController(
            CriarPagamentoUseCase criarPagamentoUseCase,
            AtualizarPagamentoUseCase atualizarPagamentoUseCase,
            BaixarPagamentoUseCase baixarPagamentoUseCase,
            CancelarPagamentoUseCase cancelarPagamentoUseCase,
            BuscarTodosPagamentosUseCase buscarTodosPagamentosUseCase,
            BuscarPagamentoByIdUseCase buscarPagamentoByIdUseCase
    ) {
        this.criarPagamentoUseCase = criarPagamentoUseCase;
        this.atualizarPagamentoUseCase = atualizarPagamentoUseCase;
        this.baixarPagamentoUseCase = baixarPagamentoUseCase;
        this.cancelarPagamentoUseCase = cancelarPagamentoUseCase;
        this.buscarTodosPagamentosUseCase = buscarTodosPagamentosUseCase;
        this.buscarPagamentoByIdUseCase = buscarPagamentoByIdUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_RECEBER_CRIAR')")
    public ResponseEntity<?> create(@Valid @RequestBody PagamentoRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(criarPagamentoUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_RECEBER_LISTAR')")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Integer cdPaciente,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtFim
    ) {
        try {
            return ResponseEntity.ok(buscarTodosPagamentosUseCase.execute(new PagamentoFiltro(cdPaciente, status, dtInicio, dtFim)));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @GetMapping("/{cdPagamento}")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_RECEBER_LISTAR')")
    public ResponseEntity<?> findById(@PathVariable Integer cdPagamento) {
        try {
            return ResponseEntity.ok(buscarPagamentoByIdUseCase.execute(cdPagamento));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_RECEBER_EDITAR')")
    public ResponseEntity<?> update(@Valid @RequestBody PagamentoRequest request) {
        try {
            return ResponseEntity.ok(atualizarPagamentoUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @PostMapping("/{cdPagamento}/baixar")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_RECEBER_BAIXAR')")
    public ResponseEntity<?> baixar(@PathVariable Integer cdPagamento, @Valid @RequestBody BaixarPagamentoRequest request) {
        try {
            return ResponseEntity.ok(baixarPagamentoUseCase.execute(cdPagamento, request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @PostMapping("/{cdPagamento}/cancelar")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_RECEBER_CANCELAR')")
    public ResponseEntity<?> cancelar(@PathVariable Integer cdPagamento) {
        try {
            return ResponseEntity.ok(cancelarPagamentoUseCase.execute(cdPagamento));
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
