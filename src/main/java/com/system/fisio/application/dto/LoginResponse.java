package com.system.fisio.application.dto;

public record LoginResponse(String token, Integer tpUsuario, Integer cdUsuario, String nmUsuario) {}
