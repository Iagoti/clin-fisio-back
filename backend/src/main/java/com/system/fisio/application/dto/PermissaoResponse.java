package com.system.fisio.application.dto;

public record PermissaoResponse(
        Integer cdPermissao,
        String cdChave,
        String dsPermissao,
        String nmModulo
) {}
