package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.UsuarioFiltro;
import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.usecase.AtualizarUsuarioUseCase;
import com.system.fisio.application.usecase.BuscarTodosUsuariosUseCase;
import com.system.fisio.application.usecase.BuscarUsuarioByIdUseCase;
import com.system.fisio.application.usecase.CriarUsuarioUseCase;
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
import java.util.List;

@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;
    private final BuscarUsuarioByIdUseCase buscarUsuarioByIdUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    public UsuarioController(
            CriarUsuarioUseCase criarUsuarioUseCase,
            BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase,
            BuscarUsuarioByIdUseCase buscarUsuarioByIdUseCase,
            AtualizarUsuarioUseCase atualizarUsuarioUseCase
    ) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.buscarTodosUsuariosUseCase = buscarTodosUsuariosUseCase;
        this.buscarUsuarioByIdUseCase = buscarUsuarioByIdUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
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
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse response = criarUsuarioUseCase.execute(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    public ResponseEntity<List<UsuarioResponse>> findAll(
            @RequestParam(required = false) String nmUsuario,
            @RequestParam(required = false, defaultValue = "1") Integer usuarioAtivo
    ) {
        UsuarioFiltro filtros = new UsuarioFiltro(nmUsuario, usuarioAtivo);
        return ResponseEntity.ok(buscarTodosUsuariosUseCase.execute(filtros));
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
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Integer cdUsuario) {
        return ResponseEntity.ok(buscarUsuarioByIdUseCase.execute(cdUsuario));
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
    public ResponseEntity<UsuarioResponse> update(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse response = atualizarUsuarioUseCase.execute(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}