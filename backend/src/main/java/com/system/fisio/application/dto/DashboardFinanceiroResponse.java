package com.system.fisio.application.dto;

import java.math.BigDecimal;

public record DashboardFinanceiroResponse(
        BigDecimal totalAReceber,
        BigDecimal recebidoNoMes,
        BigDecimal totalAPagar,
        BigDecimal pagoNoMes,
        BigDecimal saldoPeriodo,
        BigDecimal inadimplencia
) {
}
