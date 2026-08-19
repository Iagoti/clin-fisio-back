package com.system.fisio.infrastructure.apresentation.controller;

import com.system.fisio.application.dto.DeleteRoleResponse;
import com.system.fisio.application.dto.RoleRequest;
import com.system.fisio.application.dto.RoleResponse;
import com.system.fisio.application.usecase.AtualizarRoleUseCase;
import com.system.fisio.application.usecase.BuscarRoleByIdUseCase;
import com.system.fisio.application.usecase.BuscarTodasRolesUseCase;
import com.system.fisio.application.usecase.CriarRoleUseCase;
import com.system.fisio.application.usecase.DeletarRoleUseCase;
import com.system.fisio.domain.exception.BusinessException;
import com.system.fisio.infrastructure.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Perfis de acesso", description = "Endpoints de gerenciamento de perfis (roles) e suas permissões")
@RestController
@RequestMapping("/role")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

    private final CriarRoleUseCase criarRoleUseCase;
    private final AtualizarRoleUseCase atualizarRoleUseCase;
    private final DeletarRoleUseCase deletarRoleUseCase;
    private final BuscarTodasRolesUseCase buscarTodasRolesUseCase;
    private final BuscarRoleByIdUseCase buscarRoleByIdUseCase;

    public RoleController(
            CriarRoleUseCase criarRoleUseCase,
            AtualizarRoleUseCase atualizarRoleUseCase,
            DeletarRoleUseCase deletarRoleUseCase,
            BuscarTodasRolesUseCase buscarTodasRolesUseCase,
            BuscarRoleByIdUseCase buscarRoleByIdUseCase
    ) {
        this.criarRoleUseCase = criarRoleUseCase;
        this.atualizarRoleUseCase = atualizarRoleUseCase;
        this.deletarRoleUseCase = deletarRoleUseCase;
        this.buscarTodasRolesUseCase = buscarTodasRolesUseCase;
        this.buscarRoleByIdUseCase = buscarRoleByIdUseCase;
    }

    @Operation(summary = "Criar perfil", responses = {
            @ApiResponse(responseCode = "201", description = "Perfil criado com sucesso",
                    content = @Content(schema = @Schema(implementation = RoleResponse.class)))
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_GERENCIAR')")
    public ResponseEntity<?> create(@Valid @RequestBody RoleRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(criarRoleUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Listar perfis")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_LISTAR')")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(buscarTodasRolesUseCase.execute());
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Buscar perfil por ID")
    @GetMapping("/{cdRole}")
    @PreAuthorize("hasAuthority('ROLE_LISTAR')")
    public ResponseEntity<?> findById(@PathVariable Integer cdRole) {
        try {
            return ResponseEntity.ok(buscarRoleByIdUseCase.execute(cdRole));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Atualizar perfil")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_GERENCIAR')")
    public ResponseEntity<?> update(@Valid @RequestBody RoleRequest request) {
        try {
            return ResponseEntity.ok(atualizarRoleUseCase.execute(request));
        } catch (BusinessException ex) {
            return badRequest(ex);
        } catch (Exception ex) {
            return internalError(ex);
        }
    }

    @Operation(summary = "Excluir perfil", responses = {
            @ApiResponse(responseCode = "200", description = "Perfil excluído com sucesso",
                    content = @Content(schema = @Schema(implementation = DeleteRoleResponse.class)))
    })
    @DeleteMapping("/{cdRole}")
    @PreAuthorize("hasAuthority('ROLE_GERENCIAR')")
    public ResponseEntity<?> delete(@PathVariable Integer cdRole) {
        try {
            return ResponseEntity.ok(deletarRoleUseCase.execute(cdRole));
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
