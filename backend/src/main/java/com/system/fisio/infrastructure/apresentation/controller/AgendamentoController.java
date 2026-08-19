package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.AgendamentoFiltro;
import com.system.fisio.application.dto.AgendamentoRequest;
import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.application.dto.CriarReposicaoRequest;
import com.system.fisio.application.dto.DeleteAgendamentoResponse;
import com.system.fisio.application.dto.RegistrarFaltaRequest;
import com.system.fisio.application.usecase.AtualizarAgendamentoUseCase;
import com.system.fisio.application.usecase.BuscarAgendamentoByIdUseCase;
import com.system.fisio.application.usecase.BuscarTodosAgendamentosUseCase;
import com.system.fisio.application.usecase.CriarAgendamentoUseCase;
import com.system.fisio.application.usecase.CriarReposicaoUseCase;
import com.system.fisio.application.usecase.DeletarAgendamentoUseCase;
import com.system.fisio.application.usecase.RegistrarFaltaUseCase;
import com.system.fisio.domain.exception.BusinessException;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Tag(name = "Agendamentos", description = "Endpoints de gerenciamento de agendamentos")
@RestController
@RequestMapping("/agendamento")
@SecurityRequirement(name = "bearerAuth")
public class AgendamentoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgendamentoController.class);

    private final CriarAgendamentoUseCase criarAgendamentoUseCase;
    private final BuscarTodosAgendamentosUseCase buscarTodosAgendamentosUseCase;
    private final BuscarAgendamentoByIdUseCase buscarAgendamentoByIdUseCase;
    private final AtualizarAgendamentoUseCase atualizarAgendamentoUseCase;
    private final DeletarAgendamentoUseCase deletarAgendamentoUseCase;
    private final RegistrarFaltaUseCase registrarFaltaUseCase;
    private final CriarReposicaoUseCase criarReposicaoUseCase;

    public AgendamentoController(
            CriarAgendamentoUseCase criarAgendamentoUseCase,
            BuscarTodosAgendamentosUseCase buscarTodosAgendamentosUseCase,
            BuscarAgendamentoByIdUseCase buscarAgendamentoByIdUseCase,
            AtualizarAgendamentoUseCase atualizarAgendamentoUseCase,
            DeletarAgendamentoUseCase deletarAgendamentoUseCase,
            RegistrarFaltaUseCase registrarFaltaUseCase,
            CriarReposicaoUseCase criarReposicaoUseCase
    ) {
        this.criarAgendamentoUseCase = criarAgendamentoUseCase;
        this.buscarTodosAgendamentosUseCase = buscarTodosAgendamentosUseCase;
        this.buscarAgendamentoByIdUseCase = buscarAgendamentoByIdUseCase;
        this.atualizarAgendamentoUseCase = atualizarAgendamentoUseCase;
        this.deletarAgendamentoUseCase = deletarAgendamentoUseCase;
        this.registrarFaltaUseCase = registrarFaltaUseCase;
        this.criarReposicaoUseCase = criarReposicaoUseCase;
    }

    @Operation(summary = "Criar agendamento", responses = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponse.class)))
    })
    @PostMapping
    @PreAuthorize("hasAuthority('AGENDAMENTO_CRIAR')")
    public ResponseEntity<?> create(@Valid @RequestBody AgendamentoRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(criarAgendamentoUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Listar agendamentos")
    @GetMapping
    @PreAuthorize("hasAuthority('AGENDAMENTO_LISTAR')")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAgendamento,
            @RequestParam(required = false) String nmPaciente,
            @RequestParam(required = false) Integer cdPaciente,
            @RequestParam(required = false) Integer tipoAtendimento,
            @RequestParam(required = false) Integer status
    ) {
        try {
            AgendamentoFiltro filtro = new AgendamentoFiltro(dataAgendamento, nmPaciente, cdPaciente, tipoAtendimento, status);
            return ResponseEntity.ok(buscarTodosAgendamentosUseCase.execute(filtro));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Buscar agendamento por ID")
    @GetMapping("/{cdAgendamento}")
    @PreAuthorize("hasAuthority('AGENDAMENTO_LISTAR')")
    public ResponseEntity<?> findById(@PathVariable Integer cdAgendamento) {
        try {
            return ResponseEntity.ok(buscarAgendamentoByIdUseCase.execute(cdAgendamento));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Atualizar agendamento")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('AGENDAMENTO_EDITAR')")
    public ResponseEntity<?> update(@Valid @RequestBody AgendamentoRequest request) {
        try {
            return ResponseEntity.ok(atualizarAgendamentoUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Excluir agendamento", responses = {
            @ApiResponse(responseCode = "200", description = "Agendamento excluído com sucesso",
                    content = @Content(schema = @Schema(implementation = DeleteAgendamentoResponse.class)))
    })
    @DeleteMapping("/{cdAgendamento}")
    @PreAuthorize("hasAuthority('AGENDAMENTO_DELETAR')")
    public ResponseEntity<?> delete(@PathVariable Integer cdAgendamento) {
        try {
            return ResponseEntity.ok(deletarAgendamentoUseCase.execute(cdAgendamento));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Registrar falta", description = "Marca um agendamento como falta, com atestado médico opcional.")
    @PostMapping("/falta")
    @PreAuthorize("hasAuthority('AGENDAMENTO_FALTA_REGISTRAR')")
    public ResponseEntity<?> registrarFalta(@Valid @RequestBody RegistrarFaltaRequest request) {
        try {
            return ResponseEntity.ok(registrarFaltaUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Criar reposição", description = "Cria um agendamento de reposição a partir de uma falta.")
    @PostMapping("/reposicao")
    @PreAuthorize("hasAuthority('AGENDAMENTO_REPOSICAO_CRIAR')")
    public ResponseEntity<?> criarReposicao(@Valid @RequestBody CriarReposicaoRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(criarReposicaoUseCase.execute(request));
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
        LOGGER.error("Erro interno ao processar agendamento", ex);
        return ResponseEntity.status(500).body(new ErrorResponse("Erro interno do servidor", LocalDateTime.now()));
    }
}
