package com.system.fisio.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FluxoCaixaPontoResponse(
        LocalDate data,
        BigDecimal entradas,
        BigDecimal saidas,
        BigDecimal saldoDia,
        BigDecimal saldoAcumulado
) {
}
