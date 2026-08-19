package com.system.fisio.application.dto;

import java.util.Set;

public record LoginResponse(String token, Set<String> roles, Integer cdUsuario, String nmUsuario) {}
