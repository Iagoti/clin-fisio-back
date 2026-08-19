package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.ArquivoTermoResponse;
import com.system.fisio.application.dto.DeletePacienteResponse;
import com.system.fisio.application.dto.PacienteFiltro;
import com.system.fisio.application.dto.PacienteRequest;
import com.system.fisio.application.dto.PacienteResponse;
import com.system.fisio.application.usecase.AtualizarPacienteUseCase;
import com.system.fisio.application.usecase.BuscarArquivoTermoPacienteUseCase;
import com.system.fisio.application.usecase.BuscarPacienteByIdUseCase;
import com.system.fisio.application.usecase.BuscarTodosPacientesUseCase;
import com.system.fisio.application.usecase.CriarPacienteUseCase;
import com.system.fisio.application.usecase.DeletarPacienteUseCase;
import com.system.fisio.domain.exception.BusinessException;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@Tag(name = "Pacientes", description = "Endpoints de gerenciamento de pacientes")
@RestController
@RequestMapping("/paciente")
@SecurityRequirement(name = "bearerAuth")
public class PacienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteController.class);

    private final CriarPacienteUseCase criarPacienteUseCase;
    private final BuscarTodosPacientesUseCase buscarTodosPacientesUseCase;
    private final BuscarPacienteByIdUseCase buscarPacienteByIdUseCase;
    private final AtualizarPacienteUseCase atualizarPacienteUseCase;
    private final DeletarPacienteUseCase deletarPacienteUseCase;
    private final BuscarArquivoTermoPacienteUseCase buscarArquivoTermoPacienteUseCase;

    public PacienteController(
            CriarPacienteUseCase criarPacienteUseCase,
            BuscarTodosPacientesUseCase buscarTodosPacientesUseCase,
            BuscarPacienteByIdUseCase buscarPacienteByIdUseCase,
            AtualizarPacienteUseCase atualizarPacienteUseCase,
            DeletarPacienteUseCase deletarPacienteUseCase,
            BuscarArquivoTermoPacienteUseCase buscarArquivoTermoPacienteUseCase
    ) {
        this.criarPacienteUseCase = criarPacienteUseCase;
        this.buscarTodosPacientesUseCase = buscarTodosPacientesUseCase;
        this.buscarPacienteByIdUseCase = buscarPacienteByIdUseCase;
        this.atualizarPacienteUseCase = atualizarPacienteUseCase;
        this.deletarPacienteUseCase = deletarPacienteUseCase;
        this.buscarArquivoTermoPacienteUseCase = buscarArquivoTermoPacienteUseCase;
    }

    @Operation(summary = "Criar paciente", responses = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PacienteResponse.class)))
    })
    @PostMapping
    @PreAuthorize("hasAuthority('PACIENTE_CRIAR')")
    public ResponseEntity<?> create(@Valid @RequestBody PacienteRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(criarPacienteUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Listar pacientes")
    @GetMapping
    @PreAuthorize("hasAuthority('PACIENTE_LISTAR')")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String nmPaciente,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false, defaultValue = "1") Integer pacienteAtivo
    ) {
        try {
            PacienteFiltro filtro = new PacienteFiltro(nmPaciente, cpf, pacienteAtivo);
            return ResponseEntity.ok(buscarTodosPacientesUseCase.execute(filtro));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Buscar paciente por ID")
    @GetMapping("/{cdPaciente}")
    @PreAuthorize("hasAuthority('PACIENTE_LISTAR')")
    public ResponseEntity<?> findById(@PathVariable Integer cdPaciente) {
        try {
            return ResponseEntity.ok(buscarPacienteByIdUseCase.execute(cdPaciente));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Atualizar paciente")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('PACIENTE_EDITAR')")
    public ResponseEntity<?> update(@Valid @RequestBody PacienteRequest request) {
        try {
            return ResponseEntity.ok(atualizarPacienteUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Baixar/visualizar arquivo do termo de responsabilidade")
    @GetMapping("/{cdPaciente}/termo/arquivo")
    @PreAuthorize("hasAuthority('PACIENTE_TERMO_DOWNLOAD')")
    public ResponseEntity<?> baixarArquivoTermo(@PathVariable Integer cdPaciente) {
        try {
            ArquivoTermoResponse arquivo = buscarArquivoTermoPacienteUseCase.execute(cdPaciente);
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            if (arquivo.tipoConteudo() != null && !arquivo.tipoConteudo().isBlank()) {
                try {
                    mediaType = MediaType.parseMediaType(arquivo.tipoConteudo());
                } catch (Exception ignored) {
                    mediaType = MediaType.APPLICATION_OCTET_STREAM;
                }
            }
            String nomeArquivo = arquivo.nomeArquivo() != null ? arquivo.nomeArquivo() : "termo";
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename*=UTF-8''" + java.net.URLEncoder.encode(nomeArquivo, StandardCharsets.UTF_8))
                    .body(arquivo.dados());
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Excluir paciente", responses = {
            @ApiResponse(responseCode = "200", description = "Paciente excluído com sucesso",
                    content = @Content(schema = @Schema(implementation = DeletePacienteResponse.class)))
    })
    @DeleteMapping("/{cdPaciente}")
    @PreAuthorize("hasAuthority('PACIENTE_DELETAR')")
    public ResponseEntity<?> delete(@PathVariable Integer cdPaciente) {
        try {
            return ResponseEntity.ok(deletarPacienteUseCase.execute(cdPaciente));
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
        LOGGER.error("Erro interno ao processar paciente", ex);
        return ResponseEntity.status(500).body(new ErrorResponse("Erro interno do servidor", LocalDateTime.now()));
    }
}
