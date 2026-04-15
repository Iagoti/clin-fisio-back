package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.DeleteUsuarioResponse;
import com.system.fisio.application.dto.UsuarioFiltro;
import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.usecase.AtualizarUsuarioUseCase;
import com.system.fisio.application.usecase.BuscarTodosUsuariosUseCase;
import com.system.fisio.application.usecase.BuscarUsuarioByIdUseCase;
import com.system.fisio.application.usecase.CriarUsuarioUseCase;
import com.system.fisio.application.usecase.DeletarUsuarioUseCase;
import com.system.fisio.domain.exception.AcessoNegadoException;
import com.system.fisio.domain.exception.BusinessException;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;
    private final BuscarUsuarioByIdUseCase buscarUsuarioByIdUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;

    public UsuarioController(
            CriarUsuarioUseCase criarUsuarioUseCase,
            BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase,
            BuscarUsuarioByIdUseCase buscarUsuarioByIdUseCase,
            AtualizarUsuarioUseCase atualizarUsuarioUseCase,
            DeletarUsuarioUseCase deletarUsuarioUseCase
    ) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.buscarTodosUsuariosUseCase = buscarTodosUsuariosUseCase;
        this.buscarUsuarioByIdUseCase = buscarUsuarioByIdUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
    }

    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário do sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Usuário já cadastrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        try {
            UsuarioResponse response = criarUsuarioUseCase.execute(usuarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BusinessException ex) {
            ErrorResponse error = new ErrorResponse(
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(400).body(error);
        } catch (Exception ex) {
            ErrorResponse error = new ErrorResponse(
                    "Erro interno do servidor",
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados.",
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
            }
    )

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String nmUsuario,
            @RequestParam(required = false, defaultValue = "1") Integer usuarioAtivo
    ) {
        try {
            UsuarioFiltro filtros = new UsuarioFiltro(nmUsuario, usuarioAtivo);
            return ResponseEntity.ok(buscarTodosUsuariosUseCase.execute(filtros));
        } catch (BusinessException ex) {
            ErrorResponse error = new ErrorResponse(
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(400).body(error);
        } catch (Exception ex) {
            ErrorResponse error = new ErrorResponse(
                    "Erro interno do servidor",
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário.",
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
            }
    )
    @GetMapping("/{cdUsuario}")
    public ResponseEntity<?> findById(@PathVariable Integer cdUsuario) {
        try {
            return ResponseEntity.ok(buscarUsuarioByIdUseCase.execute(cdUsuario));
        } catch (BusinessException ex) {
            ErrorResponse error = new ErrorResponse(
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(400).body(error);
        } catch (Exception ex) {
            ErrorResponse error = new ErrorResponse(
                    "Erro interno do servidor",
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza um usuário do sistema.",
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
            }
    )
    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        try {
            UsuarioResponse response = atualizarUsuarioUseCase.execute(usuarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BusinessException ex) {
            ErrorResponse error = new ErrorResponse(
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(400).body(error);
        } catch (Exception ex) {
            ErrorResponse error = new ErrorResponse(
                    "Erro interno do servidor",
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(
            summary = "Deleta um usuário",
            description = "Deleta um usuário do sistema.",
            security = { @SecurityRequirement(name = "bearerAuth") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso",
                            content = @Content(schema = @Schema(implementation = DeleteUsuarioResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
            }
    )
    @DeleteMapping("/{cdUsuario}")
    public ResponseEntity<?> delete(@Valid @PathVariable Integer cdUsuario) {
        try {
            DeleteUsuarioResponse response = deletarUsuarioUseCase.execute(cdUsuario);
            return ResponseEntity.ok(response);
        } catch (AcessoNegadoException ex) {
            ErrorResponse error = new ErrorResponse(
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(403).body(error);
        } catch (BusinessException ex) {
            ErrorResponse error = new ErrorResponse(
                    ex.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(400).body(error);
        } catch (Exception ex) {
            ErrorResponse error = new ErrorResponse(
                    "Erro interno do servidor",
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(error);
        }
    }
}