package com.system.fisio.application.dto;

/** Representação enxuta de um perfil, usada dentro de UsuarioResponse.roles. */
public record RoleResumoResponse(
        Integer cdRole,
        String nmRole
) {}
