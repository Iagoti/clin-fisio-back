package com.system.fisio.domain.ports;

import com.system.fisio.application.dto.DashboardFinanceiroResponse;
import com.system.fisio.application.dto.FluxoCaixaPontoResponse;

import java.time.LocalDate;
import java.util.List;

public interface IFinanceiroRelatorioRepository {
    List<FluxoCaixaPontoResponse> gerarFluxoCaixa(LocalDate dataInicio, LocalDate dataFim);
    DashboardFinanceiroResponse gerarDashboard();
}
