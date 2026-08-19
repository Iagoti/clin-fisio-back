package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.BaixarDespesaRequest;
import com.system.fisio.application.dto.DespesaFiltro;
import com.system.fisio.application.dto.DespesaRequest;
import com.system.fisio.application.usecase.AtualizarDespesaUseCase;
import com.system.fisio.application.usecase.BaixarDespesaUseCase;
import com.system.fisio.application.usecase.BuscarDespesaByIdUseCase;
import com.system.fisio.application.usecase.BuscarTodasDespesasUseCase;
import com.system.fisio.application.usecase.CancelarDespesaUseCase;
import com.system.fisio.application.usecase.CriarDespesaUseCase;
import com.system.fisio.application.usecase.DeletarDespesaUseCase;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Financeiro - Contas a pagar", description = "Despesas da clínica")
@RestController
@RequestMapping("/financeiro/despesa")
@SecurityRequirement(name = "bearerAuth")
public class DespesaController {

    private final CriarDespesaUseCase criarDespesaUseCase;
    private final AtualizarDespesaUseCase atualizarDespesaUseCase;
    private final BaixarDespesaUseCase baixarDespesaUseCase;
    private final CancelarDespesaUseCase cancelarDespesaUseCase;
    private final BuscarTodasDespesasUseCase buscarTodasDespesasUseCase;
    private final BuscarDespesaByIdUseCase buscarDespesaByIdUseCase;
    private final DeletarDespesaUseCase deletarDespesaUseCase;

    public DespesaController(
            CriarDespesaUseCase criarDespesaUseCase,
            AtualizarDespesaUseCase atualizarDespesaUseCase,
            BaixarDespesaUseCase baixarDespesaUseCase,
            CancelarDespesaUseCase cancelarDespesaUseCase,
            BuscarTodasDespesasUseCase buscarTodasDespesasUseCase,
            BuscarDespesaByIdUseCase buscarDespesaByIdUseCase,
            DeletarDespesaUseCase deletarDespesaUseCase
    ) {
        this.criarDespesaUseCase = criarDespesaUseCase;
        this.atualizarDespesaUseCase = atualizarDespesaUseCase;
        this.baixarDespesaUseCase = baixarDespesaUseCase;
        this.cancelarDespesaUseCase = cancelarDespesaUseCase;
        this.buscarTodasDespesasUseCase = buscarTodasDespesasUseCase;
        this.buscarDespesaByIdUseCase = buscarDespesaByIdUseCase;
        this.deletarDespesaUseCase = deletarDespesaUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_PAGAR_CRIAR')")
    public ResponseEntity<?> create(@Valid @RequestBody DespesaRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(criarDespesaUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_PAGAR_LISTAR')")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Integer cdCategoriaDespesa,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtFim
    ) {
        try {
            return ResponseEntity.ok(buscarTodasDespesasUseCase.execute(new DespesaFiltro(cdCategoriaDespesa, status, dtInicio, dtFim)));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @GetMapping("/{cdDespesa}")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_PAGAR_LISTAR')")
    public ResponseEntity<?> findById(@PathVariable Integer cdDespesa) {
        try {
            return ResponseEntity.ok(buscarDespesaByIdUseCase.execute(cdDespesa));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_PAGAR_EDITAR')")
    public ResponseEntity<?> update(@Valid @RequestBody DespesaRequest request) {
        try {
            return ResponseEntity.ok(atualizarDespesaUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @PostMapping("/{cdDespesa}/baixar")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_PAGAR_BAIXAR')")
    public ResponseEntity<?> baixar(@PathVariable Integer cdDespesa, @Valid @RequestBody BaixarDespesaRequest request) {
        try {
            return ResponseEntity.ok(baixarDespesaUseCase.execute(cdDespesa, request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @PostMapping("/{cdDespesa}/cancelar")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_PAGAR_CANCELAR')")
    public ResponseEntity<?> cancelar(@PathVariable Integer cdDespesa) {
        try {
            return ResponseEntity.ok(cancelarDespesaUseCase.execute(cdDespesa));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @DeleteMapping("/{cdDespesa}")
    @PreAuthorize("hasAuthority('FINANCEIRO_CONTAS_PAGAR_DELETAR')")
    public ResponseEntity<?> delete(@PathVariable Integer cdDespesa) {
        try {
            return ResponseEntity.ok(deletarDespesaUseCase.execute(cdDespesa));
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
