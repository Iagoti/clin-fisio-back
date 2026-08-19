package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.IniciarPacoteRequest;
import com.system.fisio.application.dto.PacoteFiltro;
import com.system.fisio.application.usecase.BuscarTodosPacotesUseCase;
import com.system.fisio.application.usecase.IniciarPacoteFisioterapiaUseCase;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Financeiro - Pacotes de fisioterapia", description = "Pacotes de 10 sessões de fisioterapia")
@RestController
@RequestMapping("/financeiro/pacote")
@SecurityRequirement(name = "bearerAuth")
public class PacoteFisioterapiaController {

    private final IniciarPacoteFisioterapiaUseCase iniciarPacoteFisioterapiaUseCase;
    private final BuscarTodosPacotesUseCase buscarTodosPacotesUseCase;

    public PacoteFisioterapiaController(
            IniciarPacoteFisioterapiaUseCase iniciarPacoteFisioterapiaUseCase,
            BuscarTodosPacotesUseCase buscarTodosPacotesUseCase
    ) {
        this.iniciarPacoteFisioterapiaUseCase = iniciarPacoteFisioterapiaUseCase;
        this.buscarTodosPacotesUseCase = buscarTodosPacotesUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FINANCEIRO_PACOTE_CRIAR')")
    public ResponseEntity<?> iniciar(@Valid @RequestBody IniciarPacoteRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(iniciarPacoteFisioterapiaUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FINANCEIRO_PACOTE_LISTAR')")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Integer cdPaciente,
            @RequestParam(required = false) Integer status
    ) {
        try {
            return ResponseEntity.ok(buscarTodosPacotesUseCase.execute(new PacoteFiltro(cdPaciente, status)));
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
