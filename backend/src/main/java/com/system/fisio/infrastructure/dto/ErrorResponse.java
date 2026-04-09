package com.system.fisio.infrastructure.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        String path,
        LocalDateTime timestamp
) {}
